using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    public class NoticeTask : Task
    {
        public NoticeTask(String queue)
            : base(queue)
        {

        }
        public override void execute()
        {
            Dictionary<String, Object> message = prepareMessage();
            Ctrl.sendMessage(message, Queue);
        }

        public abstract Dictionary<string, object> prepareMessage();
    }
}
