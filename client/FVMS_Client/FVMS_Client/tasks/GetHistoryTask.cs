using FVMS_Client.files;
using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class GetHistoryTask : RequestTask
    {
        private File file;
        public GetHistoryTask(File file) : base(Queues.QHistory)
        {
            this.file = file;
        }
        public override void treatResponse(Dictionary<string, object> response)
        {
            Object outVal;
            response.TryGetValue("changes", out outVal);
            String outString = outVal.ToString();
            List<File> files = FilesHandler.getInstance().getFiles(outString);
            files.Reverse();
            FormsHandler.addFilesToHistoryGrid(files);
        }

        public override Dictionary<string, object> prepareMessage()
        {
            Dictionary<string, object> msg = new Dictionary<string, object>();
            msg.Add("fid", file.id);
            msg.Add("pid", file.pid);
            return msg;
        }
    }
}
