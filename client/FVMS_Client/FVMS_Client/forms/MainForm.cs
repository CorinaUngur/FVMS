using FVMS_Client.beans;
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
    public partial class MainForm : Form
    {
        private Dictionary<String, Folder> nodesFoldersDict = new Dictionary<String,Folder>();
        public MainForm()
        {
            InitializeComponent();
        }
        public void GenerateTree(List<Folder> rootFolders)
        {
            foreach (Folder fld in rootFolders)
            {
                foldersTree.Nodes.Add(addFolderInTree(fld));
            }
        }
        private TreeNode addFolderInTree(Folder fld)
        {
            TreeNode thisNode = new TreeNode(fld.name);
            if (fld.getChilds() == null || fld.getChilds().Count() == 0)
            {
                return thisNode;
            }
            else
            {
                int index = 0;
                TreeNode[] children = new TreeNode[fld.getChilds().Count()];
                foreach (Folder f in fld.getChilds())
                {
                    TreeNode childNode = addFolderInTree(f);
                    childNode.Name = f.path;
                    nodesFoldersDict.Add(childNode.Name, f);
                    children[index] = childNode;
                }
                thisNode.Nodes.AddRange(children);
            }
            return thisNode;
        }
        private void addFilesToNode(TreeNode node, Folder folder)
        {
            foreach(File f in folder.GetFiles()){
                TreeNode file = new TreeNode(f.name);
                node.Nodes.Add(file);
            }
        }
        private void logOutButton_Click(object sender, EventArgs e)
        {
            (new LogOutTask()).execute();
        }

        private void Page2Button_Click(object sender, EventArgs e)
        {
            panel3.BringToFront();
            panel3.Focus();
        }

        private void foldersTree_AfterSelect(object sender, TreeViewEventArgs e)
        {
            Folder folder;
            nodesFoldersDict.TryGetValue(e.Node.Name, out folder);
            if (folder != null)
            {
                filesGrid.DataSource = folder.GetFiles();
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Controller.getInstance().Init();
        }

        private void whenFileSelect(object sender, System.Windows.Forms.DataGridViewCellEventArgs e)
        {
            (new GetHistoryTask(filesGrid.CurrentRow.DataBoundItem)).execute();
        }
    }
}
