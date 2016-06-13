using FVMS_Client.beans;
using FVMS_Client.files;
using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class InitTask : RequestTask
    {
        public InitTask()
            : base(Queues.QInit)
        {

        }
        public override void treatResponse(Dictionary<string, object> response)
        {
            Console.Write(response);
            Object outVal;
            response.TryGetValue("projects", out outVal);
            String outString = outVal.ToString();
            List<Folder> folders = FilesHandler.getInstance().initializeFolders(outString);
            FormsHandler.createProjectsTree(folders);
        }

        public override Dictionary<string, object> prepareMessage()
        {
            Dictionary<string, object> mesg = new Dictionary<string,object>();
            return mesg;
        }
    }
}
