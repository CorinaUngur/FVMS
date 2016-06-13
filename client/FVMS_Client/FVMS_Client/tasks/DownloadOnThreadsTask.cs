using FVMS_Client.files;
using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class DownloadOnThreadsTask : Task
    {
        private File file;
        public DownloadOnThreadsTask(File file, String path) : base(Queues.QFileDownload)
        {
            this.file = file;
            this.file.boundedFilePath = path;
        }
        public void treatResponse(Dictionary<string, object> response)
        {
            object hash;
            response.TryGetValue("hash", out hash);
            object queue;
            response.TryGetValue("queue", out queue);
            object noOfBlocks;
            response.TryGetValue("noOfBlocks", out noOfBlocks);

            Controller.getInstance().consumeFile(queue.ToString(), Int32.Parse(noOfBlocks.ToString()), hash.ToString(), file);
        }

        public override void execute()
        {
            Dictionary<String, Object> response = Ctrl.ConsumeOneMessage(Queues.QFileDownload);
            treatResponse(response);
        }
    }
}
