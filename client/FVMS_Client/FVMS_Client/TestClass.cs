using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
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
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            LoginForm loginForm = new LoginForm();
            FormsHandler.setLoginForm(loginForm);
            Application.Run(loginForm);

        }


        


    }
}
