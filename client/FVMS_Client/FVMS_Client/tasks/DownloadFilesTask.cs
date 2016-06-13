using FVMS_Client.files;
using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class DownloadFilesTask : NoticeTask
    {
        private List<File> files;
        private string path;
        public DownloadFilesTask(List<File> files, String path)
            : base(Queues.QDownload)
        {
            this.files = files;
            this.path = path;
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
            foreach (File f in files)
            {
                Thread thread = new Thread(delegate()
                {
                    (new DownloadOnThreadsTask(f, path + "/" + f.name)).execute();
                });
                thread.Start();
            }
            return message;

        }
    }
}
