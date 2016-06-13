using FVMS_Client.beans;
using FVMS_Client.files;
using FVMS_Client.tasks;
using FVMS_Client.tools;
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
            nameLabel.Text = LoggedUser.Name;
        }
        public void GenerateTree(List<Folder> rootFolders)
        {
            nodesFoldersDict.Clear();
            foldersTree.Nodes.Clear();
            foreach (Folder fld in rootFolders)
            {
                foldersTree.Nodes.Add(addFolderInTree(fld));
            }
        }
        private TreeNode addFolderInTree(Folder fld)
        {
            TreeNode thisNode = new TreeNode(fld.name);
            addNodeFolderPair(fld, thisNode);
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
                    children[index] = childNode;
                    index++;
                }
                thisNode.Nodes.AddRange(children);
            }
            return thisNode;
        }

        private void addNodeFolderPair(Folder fld, TreeNode thisNode)
        {
            string key = fld.path;
            if (nodesFoldersDict.ContainsKey(key))
            {
                nodesFoldersDict.Remove(key);
            }
            thisNode.Name = key;
            nodesFoldersDict.Add(key, fld);
        }
        private void logOutButton_Click(object sender, EventArgs e)
        {
            (new LogOutTask()).execute();
            this.Close();
            LoginForm form = new LoginForm();
            form.Show();
        }

        private void foldersTree_AfterSelect(object sender, TreeViewEventArgs e)
        {
            Folder folder;
            nodesFoldersDict.TryGetValue(e.Node.Name, out folder);

            if (folder != null && folder.GetFiles().Count() > 0)
            {
                List<File> files = folder.GetFiles();
                filesGrid.DataSource = files;

                setSubsolTabData(subsolTabControl.SelectedTab, files.First());
            }
            else
            {
                filesGrid.DataSource = null;
            }
        }

        private void refreshButton_Click(object sender, EventArgs e)
        {
            foldersTree.Nodes.Clear();
            Controller.getInstance().Init();
        }

        private void whenFileSelected(object sender, EventArgs e)
        {
            File file = (File)filesGrid.CurrentRow.DataBoundItem;
            setSubsolTabData(subsolTabControl.SelectedTab, file);

        }

        private void whenTabChanges(object sender, TabControlCancelEventArgs e)
        {
             if(filesGrid.Rows.Count > 0){
                File file = (File)filesGrid.CurrentRow.DataBoundItem;
                setSubsolTabData(e.TabPage, file);
                subsolTabControl.SelectedTab = e.TabPage;
                
            }
        }

        private void setSubsolTabData(TabPage page, File file)
        {
            if (file != null)
            {
                if (page.Equals(historyPage))
                {
                    (new GetHistoryTask(file)).execute();
                    historyPage.Show();
                }
                else
                {
                    commentLabel.Text = file.lastComment;
                }
            }

        }

        internal void setHistoryGrid(List<File> files)
        {
            historyGrid.DataSource = files;
        }

        private void saveFilesButton_Click(object sender, EventArgs e)
        {
            if (filesGrid.SelectedRows.Count == 0)
            {
                MessagePopup popup = new MessagePopup(Messages.Attention_SelectOneFile.ToString());
                popup.ShowDialog();
            }
            else
            {
                List<File> changedFiles = getChangedSelectedFiles();
                if (changedFiles.Count() == 0)
                {
                    MessagePopup popup = new MessagePopup(Messages.Attention_SelectOneChangedFile.ToString());
                    popup.ShowDialog();
                }
                else
                {
                    SaveFilesPopup savefileP = new SaveFilesPopup(getChangedSelectedFiles());
                    savefileP.ShowDialog();
                }
            }
        }
        public List<File> getSelectedFilesInGrid()
        {
            List<File> files = new List<File>();
            foreach (DataGridViewRow row in getSelectedRows())
            {
                File file = (File)row.DataBoundItem;
                files.Add(file);
            }
            return files;
        }
        internal void setFileChanged(File file)
        {
            throw new NotImplementedException();
        }

        public TreeNode getSelectedFolder()
        {
            return foldersTree.SelectedNode;
        }
        public DataGridViewSelectedRowCollection getSelectedRows()
        {
            return filesGrid.SelectedRows;
        }

        private void downloadFileButton_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog folderBrowser = new FolderBrowserDialog();
            folderBrowser.ShowNewFolderButton = false;
            folderBrowser.Description = "Select the folder where you want to save the files.";
            DialogResult result = folderBrowser.ShowDialog();
            if (result.Equals(DialogResult.OK))
            {
                String path = folderBrowser.SelectedPath;
                List<File> files = getSelectedFilesInGrid();
                List<File> filesToChange = getUpToDateSelectedFiles();
                if (filesToChange.Count() < files.Count())
                {
                    MessagePopup popup = new MessagePopup(Messages.Warning_FilesWillBeOveritten.ToString());
                    popup.ShowDialog();
                    if (!popup.Canceled)
                    {
                        (new DownloadFilesTask(filesToChange, path)).execute();
                    }
                }
                else
                {
                    (new DownloadFilesTask(filesToChange, path)).execute();
                }
            }
        }

        private List<File> getUpToDateSelectedFiles()
        {
            List<File> files = getSelectedFilesInGrid();
            List<File> filesToChange = new List<File>();
            foreach (File f in files)
            {
                if (f.fileStatus.Equals(FileStatus.UPTODATE))
                {
                    filesToChange.Add(f);
                }
            }
            return filesToChange;
        }
        private List<File> getChangedSelectedFiles()
        {
            List<File> files = getSelectedFilesInGrid();
            List<File> filesToChange = new List<File>();
            foreach (File f in files)
            {
                if (!f.fileStatus.Equals(FileStatus.UPTODATE))
                {
                    filesToChange.Add(f);
                }
            }
            return filesToChange;
        }

        private void collapseButton_Click(object sender, EventArgs e)
        {
            foldersTree.CollapseAll();
        }

        private void expandButton_Click(object sender, EventArgs e)
        {
            foldersTree.ExpandAll();
        }

        private void deleteFileButton_Click(object sender, EventArgs e)
        {
            List<File> files = getSelectedFilesInGrid();
            Folder folder;
            nodesFoldersDict.TryGetValue(foldersTree.SelectedNode.Name, out folder);
            List<File> filesOnServer = new List<File>();
            foreach (File f in files)
            {
                switch (f.fileStatus)
                {
                    case FileStatus.NEW: folder.removeFile(f); filesGrid.DataSource = folder.GetFiles(); break;
                    case FileStatus.DELETED: break;
                    default: f.fileStatus = FileStatus.DELETED; filesOnServer.Add(f); break;
                }
            }
        }

        private void addFileButton_Click(object sender, EventArgs e)
        {
            TreeNode node = foldersTree.SelectedNode;
            if (node != null)
            { 
            Folder folder;
            nodesFoldersDict.TryGetValue(node.Name, out folder);

            OpenFileDialog openFileDialog = new OpenFileDialog();
            DialogResult result = openFileDialog.ShowDialog();
            if (result.Equals(DialogResult.OK))
            {
                String[] filesToAdd = openFileDialog.FileNames;
                foreach (String file_name in filesToAdd)
                {
                    string path;
                    if (folder.path.Equals(folder.name))
                    {
                        path = "";
                    }
                    else
                    {
                        path = folder.path.Substring(folder.name.Length - 1);
                    }
                    
                    File file = new File.FileInFolder(folder.pid, path, file_name, LoggedUser.Name);
                    folder.addFile(file);
                    filesGrid.DataSource = folder.GetFiles();
                }
            }
            }
            else
            {
                (new MessagePopup(Messages.Attention_NoFolderSelected.ToString())).ShowDialog();
            }
        }
    }
}
