using FVMS_Client.forms;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FVMS_Client
{
    class FormsHandler
    {
        private static LoginForm loginForm;
        private static MainForm mainForm;

        public static void setLoginForm(LoginForm form)
        {
            loginForm = form;
        }

        public static void setMainForm(MainForm form)
        {
            mainForm = form;
        }

        public static void setLoginResponse(string response)
        {
            loginForm.Set_LoginResponseMessage(response);
        }

        public static void ReplaceLoginWithMainForm()
        {
            loginForm.HideFormOnProperThread(null, EventArgs.Empty);
            setMainForm(new MainForm());
            Application.Run(mainForm);
        }
    }
}
