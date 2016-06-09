using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class LogOutTask : NoticeTask
    {
        public LogOutTask()
            : base(Queues.QLogout)
        {

        }

        public override Dictionary<string, object> prepareMessage()
        {
            Dictionary<string, object> msg = new Dictionary<string, object>();
            msg.Add("uid", LoggedUser.uid);
            return msg;
        }
    }
}
