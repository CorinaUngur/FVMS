using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class GetHistoryTask : RequestTask
    {
        private object p;

        public GetHistoryTask(object p) : base(Ctrl.QHistory)
        {
            // TODO: Complete member initialization
            this.p = p;
        }
        public override void treatResponse(Dictionary<string, object> response)
        {
            throw new NotImplementedException();
        }

        public override Dictionary<string, object> prepareMessage()
        {
            throw new NotImplementedException();
        }
    }
}
