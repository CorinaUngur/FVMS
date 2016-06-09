using FVMS_Client.forms;
using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FVMS_Client
{
    class TestClass
    {
        private static TestClass instance;
        private TestClass()
        {

        }

        public static TestClass getInstance()
        {
            if (instance == null)
            {
                instance = new TestClass();
            }
            return instance;
        }
    
        
        [STAThread]
        static void Main(string[] args)
        {

            LoggedUser.uid = -1;
            LoggedUser.Name = "buddy";
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            LoginForm loginForm = new LoginForm();
            MainForm mainForm = new MainForm();
            FormsHandler.setMainForm(mainForm);
            FormsHandler.setLoginForm(loginForm);
            Application.Run(loginForm);

        }


    }
}
