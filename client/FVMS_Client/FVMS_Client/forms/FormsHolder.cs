using FVMS_Client.files;
using FVMS_Client.forms;
using FVMS_Client.tools;
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

        public static void initializeMainForm()
        {
            mainForm.nameLabel.Text = LoggedUser.Name;
        }
        public static void setLoginResponse(string response)
        {
            loginForm.Set_LoginResponseMessage(response);
        }

        public static void ReplaceLoginWithMainForm()
        {
            DoOnUIThread(loginForm, delegate()
            {
                loginForm.Hide();
            });
            DoOnUIThread(mainForm, delegate()
            {
                mainForm.Show();
            });
        }

        private static void DoOnUIThread(Form f, MethodInvoker d)
        {
            if (f.InvokeRequired) { f.Invoke(d); } else { d(); }
        }

        internal static void createProjectsTree()
        {
            DoOnUIThread(mainForm, delegate()
            {
                mainForm.GenerateTree(FilesHandler.getInstance().getFoldersList());
            });
            
        }
    }
}
