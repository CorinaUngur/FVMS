using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FVMS_Client
{
    public partial class LoginForm : Form
    {
        public LoginForm()
        {
            InitializeComponent();
        }

        private void LoginForm_Load(object sender, EventArgs e)
        {
            if (InvokeRequired) Invoke(new EventHandler(LoginForm_Load), sender, e);
            else
            {
                    username_textbox.Focus();
            }

        }
        public void HideFormOnProperThread(object sender, EventArgs e)
        {
            if (InvokeRequired) Invoke(new EventHandler(HideFormOnProperThread), sender, e);
            else
            {
                this.Hide();
            }
        }

        private void click_loginButton(object sender, EventArgs e)
        {
            Controller.getInstance().Login(GetUserName(), GetPassword());
        }

        private void cancel_button_Click(object sender, EventArgs e)
        {
            this.Hide();
        }
    }
}
