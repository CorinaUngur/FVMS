
using FVMS_Client.beans;
using FVMS_Client.files;
using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client
{
    class JSONManipulator
    {
        public static byte[] getSendingJason(Dictionary<String, Object> response)
        {
            byte[] message;
            message = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(response));
            return message;
        }

        public static Dictionary<String, Object> getResponseDictionary(byte[] json)
        {
            string message = Encoding.UTF8.GetString(json);
            return JsonConvert.DeserializeObject<Dictionary<string, Object>>(message);
        }

        internal static List<T> DeserializeList<T>(string jsonString)
        {
            return JsonConvert.DeserializeObject<List<T>>(jsonString);
        }

        internal static Dictionary<T1, T2> DeserializeDict<T1, T2>(String map)
        {
            return JsonConvert.DeserializeObject<Dictionary<T1, T2>>(map);
        }
    }
}
