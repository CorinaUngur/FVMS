using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FVMS_Client.tasks;
using FVMS_Client.files;
using System.Runtime.Serialization.Formatters.Binary;
using FVMS_Client.tools;

namespace FVMS_Client
{
    public class Controller
    {
        private IConnection connection;
        private IModel channel;
        private string replyQueueName;
        private QueueingBasicConsumer consumer;
        private static Controller instance = null;

        private Controller()
        {
            var factory = new ConnectionFactory() { HostName = "localhost" };
            connection = factory.CreateConnection();
            channel = connection.CreateModel();
            replyQueueName = channel.QueueDeclare().QueueName;
            consumer = new QueueingBasicConsumer(channel);
            channel.BasicConsume(queue: replyQueueName,
                                 noAck: true,
                                 consumer: consumer);
        }

        public static Controller getInstance()
        {
            if (instance == null)
            {
                instance = new Controller();
            }
            return instance;
        }

        public void Login(String userName, String password)
        {
            (new LoginTask(userName, password)).execute();
        }
        public void Init()
        {
            (new InitTask()).execute();
        }
        public void sendMessage(Dictionary<String, Object> dictMessage, String queue, string corrId)
        {
            var props = channel.CreateBasicProperties();
            props.ReplyTo = replyQueueName;
            props.CorrelationId = corrId;
            dictMessage.Add("uid", LoggedUser.uid);
            var messageBytes = JSONManipulator.getSendingJason(dictMessage);
            channel.BasicPublish(exchange: "",
                                 routingKey: queue,
                                 basicProperties: props,
                                 body: messageBytes);
        }

        internal void sendMessage(Dictionary<string, object> message, string Queue)
        {
            var messageBytes = JSONManipulator.getSendingJason(message);
            channel.BasicPublish(exchange: "",
                                 routingKey: Queue,
                                 basicProperties: null,
                                 body: messageBytes);
        }

        public Dictionary<String, Object> getResponse(String corrId)
        {
            var ea = (BasicDeliverEventArgs)consumer.Queue.Dequeue();
            if (ea.BasicProperties.CorrelationId == corrId)
            {
                return JSONManipulator.getResponseDictionary(ea.Body);
            }
            else
            {
                return null;
            }
        }

        public void consumeFile(string Queue, int noOfBlocks, string hash, File file){
            QueueingBasicConsumer fileConsumer = new QueueingBasicConsumer(channel);
            channel.BasicConsume(queue: Queue,
                                 noAck: true,
                                 consumer: fileConsumer);
            for (int i = 0; i < noOfBlocks; i++)
            {
                var ea = (BasicDeliverEventArgs)fileConsumer.Queue.Dequeue();
                Dictionary<string, object> blockInfo = JSONManipulator.getResponseDictionary(ea.Body);
                object content;
                blockInfo.TryGetValue("fileBlock", out content);
                
                file.appendContent(ObjectToByteArray(content));
            }
            if (file.checkContent(hash))
            {
                //Do something here
                file.createFileWatcherOnFile();
            }
        }

        private static byte[] ObjectToByteArray(Object obj)
        {
            BinaryFormatter bf = new BinaryFormatter();
            using (var ms = new System.IO.MemoryStream())
            {
                bf.Serialize(ms, obj);
                return ms.ToArray();
            }
        }
        internal Dictionary<string, object> ConsumeOneMessage(string Queue)
        {
            QueueingBasicConsumer fileConsumer = new QueueingBasicConsumer(channel);
            channel.BasicConsume(queue: Queue,
                                 noAck: true,
                                 consumer: fileConsumer);
            var ea = (BasicDeliverEventArgs)fileConsumer.Queue.Dequeue();
            return JSONManipulator.getResponseDictionary(ea.Body);
        }
    }
}
