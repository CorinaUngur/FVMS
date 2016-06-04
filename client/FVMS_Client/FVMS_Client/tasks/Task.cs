using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    abstract class Task
    {
        public static Controller Ctrl = Controller.getInstance();
        public String Queue { get; private set; }

        public Task(String queue){
            this.Queue = queue;
        }

        public abstract void execute();

    }
}
