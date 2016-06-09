using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FVMS_Client.forms
{
    public partial class MessagePopup : Form
    {
        public bool Canceled { get; private set; }
        public MessagePopup(String text)
        {
            InitializeComponent();
            messageLabel.Text = text;
        }

        private void okButton_Click(object sender, EventArgs e)
        {
            this.Close();
            this.Canceled = false;
        }

        private void cancleMessagePopupButton_Click(object sender, EventArgs e)
        {
            this.Canceled = true;
        }
    }
}
