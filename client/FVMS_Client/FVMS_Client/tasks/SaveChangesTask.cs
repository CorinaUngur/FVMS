using FVMS_Client.files;
using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class SaveChangeTask : RequestTask
    {
        List<File>files;
        string message;
        public SaveChangeTask(List<File> files, String message) : base (Queues.QSaveChanges) {
            this.files = files;
            this.message = message;
    }

        public override Dictionary<string, object> prepareMessage()
        {
            Dictionary<string, object> response = new Dictionary<string, object>();
            int pid = files.First().pid;
            Dictionary<String, object> fileDetails = new Dictionary<string, object>();
            List<Dictionary<String, object>> filesDict = new List<Dictionary<string, object>>();
            foreach (File f in files)
            {
                fileDetails.Add("fileStatus", f.fileStatus);
                fileDetails.Add("file_rpath", f.path);
                System.IO.FileStream fs = System.IO.File.Open(f.boundedFilePath, System.IO.FileMode.Open);
                long fileSize = fs.Length;
                MD5 md5 = MD5.Create();
                String hash = md5.ComputeHash(fs).ToString() ;
                fs.Close();
                fileDetails.Add("fileSize", fileSize);
                fileDetails.Add("blockSize", Config.blockSize);
                long noOfBlocks = fileSize / Config.blockSize;
                if (fileSize % Config.blockSize > 0)
                {
                    noOfBlocks++;
                }
                fileDetails.Add("blocksNo", noOfBlocks);
                fileDetails.Add("fid", f.id);
                fileDetails.Add("hash", hash);
                filesDict.Add(fileDetails);
            }
            response.Add("files", filesDict);
            response.Add("uid", LoggedUser.uid);
            response.Add("pid", pid);
            response.Add("message", message);
            return response;
        }

        public override void treatResponse(Dictionary<string, object> response)
        {
            object outobj;
            response.TryGetValue("authorized",out outobj);
            object queues;
            response.TryGetValue("queues", out queues);
            Dictionary<string, string> queuesNames = JSONManipulator.DeserializeDict<String, string>(queues.ToString());
            int authorized = Int32.Parse(outobj.ToString());
            if (authorized==1)
            {
                foreach (File f in files)
                {
                    if (queuesNames.Keys.Contains(f.path))
                    {
                        string qname;
                        queuesNames.TryGetValue(f.path, out qname);
                        Thread thread = new Thread(delegate()
                        {
                            
                            (new UploadFileOnParts(qname, f)).execute();
                        });
                        thread.Start();
                    }
                    f.fileStatus = FileStatus.UPTODATE;
                }
            }
            else
            {
                FormsHandler.popup(Messages.Attention_NotAutorized);
            }
        }
    }
}
