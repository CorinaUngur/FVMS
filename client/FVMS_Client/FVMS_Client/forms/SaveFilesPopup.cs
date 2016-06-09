using FVMS_Client.files;
using FVMS_Client.tasks;
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
    public partial class SaveFilesPopup : Form
    {
        List<File> files;
        public SaveFilesPopup(List<File> files)
        {
                InitializeComponent();
                this.files = files;
                foreach (File f in files)
                {
                    this.filesListBox.Items.Add(f.path);
                }
            
        }

        private void saveFilesButton_Click(object sender, EventArgs e)
        {
            (new SaveChangeTask(files, commitMessageTextBox.Text)).execute();
            this.Close();

        }

        private void cancelSavingFilesButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
