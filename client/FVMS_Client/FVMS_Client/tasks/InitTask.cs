using FVMS_Client.files;
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
            : base(Controller.getInstance().QInit)
        {

        }
        public override void treatResponse(Dictionary<string, object> response)
        {
            Console.Write(response);
            Object outVal;
            response.TryGetValue("projects", out outVal);
            String outString = outVal.ToString();
            Console.Write(outVal);
            FilesHandler.getInstance().createFoldersMap(outString);
            FormsHandler.createProjectsTree();
        }

        public override Dictionary<string, object> prepareMessage()
        {
            Dictionary<string, object> mesg = new Dictionary<string,object>();
            mesg.Add("uid", this.Ctrl.uid);
            return mesg;
        }
    }
}
