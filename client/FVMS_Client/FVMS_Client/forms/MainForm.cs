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
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private void Page1Button_Click(object sender, EventArgs e)
        {
            panel2.BringToFront();
            panel2.Focus();
        }

        private void Page2Button_Click(object sender, EventArgs e)
        {
            panel3.BringToFront();
            panel3.Focus();
        }
    }
}
