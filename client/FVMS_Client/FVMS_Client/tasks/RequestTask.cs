using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    abstract class RequestTask : Task
    {
        public RequestTask(String queue_name) : base(queue_name)
        {
        }
        public override void execute()
        {
            var corrId = Guid.NewGuid().ToString();
            Dictionary<String, Object> message = prepareMessage();
            Ctrl.sendMessage(message, Queue, corrId);
            Dictionary<String, Object> response = Ctrl.getResponse(corrId);
            treatResponse(response);
        }

        public abstract void treatResponse(Dictionary<string, object> response);

        public abstract Dictionary<String, Object> prepareMessage();
    }
}
