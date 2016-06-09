using FVMS_Client.files;
using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class DownloadFileTask : RequestTask
    {
        List<File> files;
        string path;
        public DownloadFileTask(List<File> files, String path) : base(Queues.QDownload)
        {
            this.files = files;
            this.path = path;
        }
        public override void treatResponse(Dictionary<string, object> response)
        {
            Object contents;
            response.TryGetValue("contents", out contents);
            Dictionary<int, byte[]> filesContents = JSONManipulator.DeserializeDict<byte[]>(contents.ToString());
            foreach(File f in files)
            {
                byte[] outParam;
                filesContents.TryGetValue(f.id, out outParam);
                f.setFileOnDisk(path + "/" + f.name, outParam);
            }
        }

        public override Dictionary<string, object> prepareMessage()
        {
            Dictionary<string, object> message = new Dictionary<string, object>();
            List<int> filesIDs = new List<int>();
            int pid = files.First().pid;
            foreach (File f in files)
            {
                filesIDs.Add(f.id);
            }
            message.Add("fids", filesIDs);
            message.Add("uid", LoggedUser.uid);
            message.Add("pid", pid); 
            return message;

        }
    }
}
