
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client
{
    class JSONManipulator
    {
        public static byte[] getSendingJason(Dictionary<String, String> response)
        {
            byte[] message;
            HashSet<KeyValuePair<String, String>> values_list = new HashSet<KeyValuePair<String, String>>();
            foreach (KeyValuePair<String, String> entry in response)
            {
                values_list.Add(entry);
            }
           //message  = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(values_list));
            message = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(response));
            return message;
        }

        public static Dictionary<String, String> getResponseDictionary(byte[] json)
        {
            string message = Encoding.UTF8.GetString(json);
            return JsonConvert.DeserializeObject<Dictionary<string, string>>(message);
        }
    }
}
