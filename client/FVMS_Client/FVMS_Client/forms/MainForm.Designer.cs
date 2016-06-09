using FVMS_Client.tools;
using System;
namespace FVMS_Client.forms
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.menu_panel = new System.Windows.Forms.Panel();
            this.collapseButton = new System.Windows.Forms.Button();
            this.expandButton = new System.Windows.Forms.Button();
            this.refreshButton = new System.Windows.Forms.Button();
            this.foldersTree = new System.Windows.Forms.TreeView();
            this.helloLabel = new System.Windows.Forms.Label();
            this.nameLabel = new System.Windows.Forms.Label();
            this.logoutButton = new System.Windows.Forms.Button();
            this.filesGridPannel = new System.Windows.Forms.Panel();
            this.filesGrid = new System.Windows.Forms.DataGridView();
            this.id = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.fileStatusDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.nameDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lastModifiedDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.authNameDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lastCommentDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.fileBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.addFileButton = new System.Windows.Forms.Button();
            this.deleteFileButton = new System.Windows.Forms.Button();
            this.downloadFileButton = new System.Windows.Forms.Button();
            this.saveFilesButton = new System.Windows.Forms.Button();
            this.subsolTabControl = new System.Windows.Forms.TabControl();
            this.CommentPage = new System.Windows.Forms.TabPage();
            this.commentLabel = new System.Windows.Forms.Label();
            this.historyPage = new System.Windows.Forms.TabPage();
            this.historyGrid = new System.Windows.Forms.DataGridView();
            this.idDataGridHistoryColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.nameDataGridHistoryColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.authNameDataGridHistoryColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lastModifiedDataGridHistoryColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lastCommentDataGridHistoryColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.menu_panel.SuspendLayout();
            this.filesGridPannel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.filesGrid)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.fileBindingSource)).BeginInit();
            this.subsolTabControl.SuspendLayout();
            this.CommentPage.SuspendLayout();
            this.historyPage.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.historyGrid)).BeginInit();
            this.SuspendLayout();
            // 
            // menu_panel
            // 
            this.menu_panel.Controls.Add(this.collapseButton);
            this.menu_panel.Controls.Add(this.expandButton);
            this.menu_panel.Controls.Add(this.refreshButton);
            this.menu_panel.Controls.Add(this.foldersTree);
            this.menu_panel.Location = new System.Drawing.Point(0, 41);
            this.menu_panel.Name = "menu_panel";
            this.menu_panel.Size = new System.Drawing.Size(201, 503);
            this.menu_panel.TabIndex = 0;
            // 
            // collapseButton
            // 
            this.collapseButton.Location = new System.Drawing.Point(59, -1);
            this.collapseButton.Name = "collapseButton";
            this.collapseButton.Size = new System.Drawing.Size(22, 22);
            this.collapseButton.TabIndex = 7;
            this.collapseButton.Text = "-";
            this.collapseButton.UseVisualStyleBackColor = true;
            this.collapseButton.Click += new System.EventHandler(this.collapseButton_Click);
            // 
            // expandButton
            // 
            this.expandButton.Location = new System.Drawing.Point(31, -1);
            this.expandButton.Name = "expandButton";
            this.expandButton.Size = new System.Drawing.Size(22, 22);
            this.expandButton.TabIndex = 6;
            this.expandButton.Text = "+";
            this.expandButton.UseVisualStyleBackColor = true;
            this.expandButton.Click += new System.EventHandler(this.expandButton_Click);
            // 
            // refreshButton
            // 
            this.refreshButton.BackgroundImage = global::FVMS_Client.Properties.Resources.ie_refresh_button;
            this.refreshButton.Location = new System.Drawing.Point(3, -1);
            this.refreshButton.Name = "refreshButton";
            this.refreshButton.Size = new System.Drawing.Size(22, 22);
            this.refreshButton.TabIndex = 3;
            this.refreshButton.UseVisualStyleBackColor = true;
            this.refreshButton.Click += new System.EventHandler(this.refreshButton_Click);
            // 
            // foldersTree
            // 
            this.foldersTree.Location = new System.Drawing.Point(3, 21);
            this.foldersTree.Name = "foldersTree";
            this.foldersTree.Size = new System.Drawing.Size(194, 478);
            this.foldersTree.TabIndex = 2;
            this.foldersTree.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.foldersTree_AfterSelect);
            this.foldersTree.HideSelection = false;
            // 
            // helloLabel
            // 
            this.helloLabel.AutoSize = true;
            this.helloLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.helloLabel.Location = new System.Drawing.Point(886, 1);
            this.helloLabel.Name = "helloLabel";
            this.helloLabel.Size = new System.Drawing.Size(44, 17);
            this.helloLabel.TabIndex = 5;
            this.helloLabel.Text = "Hello,";
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.nameLabel.Location = new System.Drawing.Point(886, 20);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.nameLabel.Size = new System.Drawing.Size(47, 17);
            this.nameLabel.TabIndex = 4;
            this.nameLabel.Text = "buddy";
            // 
            // logoutButton
            // 
            this.logoutButton.BackgroundImage = global::FVMS_Client.Properties.Resources.logout;
            this.logoutButton.Location = new System.Drawing.Point(939, 2);
            this.logoutButton.Name = "logoutButton";
            this.logoutButton.Size = new System.Drawing.Size(33, 36);
            this.logoutButton.TabIndex = 0;
            this.logoutButton.UseVisualStyleBackColor = true;
            this.logoutButton.Click += new System.EventHandler(this.logOutButton_Click);
            // 
            // filesGridPannel
            // 
            this.filesGridPannel.BackColor = System.Drawing.SystemColors.Control;
            this.filesGridPannel.Controls.Add(this.filesGrid);
            this.filesGridPannel.Location = new System.Drawing.Point(203, 41);
            this.filesGridPannel.Name = "filesGridPannel";
            this.filesGridPannel.Size = new System.Drawing.Size(769, 355);
            this.filesGridPannel.TabIndex = 0;
            // 
            // filesGrid
            // 
            this.filesGrid.AllowUserToAddRows = false;
            this.filesGrid.AllowUserToDeleteRows = false;
            this.filesGrid.AutoGenerateColumns = false;
            this.filesGrid.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.filesGrid.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.filesGrid.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.Single;
            this.filesGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.filesGrid.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.id,
            this.fileStatusDataGridViewTextBoxColumn,
            this.nameDataGridViewTextBoxColumn,
            this.lastModifiedDataGridViewTextBoxColumn,
            this.authNameDataGridViewTextBoxColumn,
            this.lastCommentDataGridViewTextBoxColumn});
            this.filesGrid.DataSource = this.fileBindingSource;
            this.filesGrid.GridColor = System.Drawing.SystemColors.ControlLight;
            this.filesGrid.Location = new System.Drawing.Point(4, 3);
            this.filesGrid.Name = "filesGrid";
            this.filesGrid.ReadOnly = true;
            this.filesGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.filesGrid.Size = new System.Drawing.Size(759, 352);
            this.filesGrid.TabIndex = 0;
            this.filesGrid.SelectionChanged += new System.EventHandler(this.whenFileSelected);
            // 
            // id
            // 
            this.id.DataPropertyName = "id";
            this.id.HeaderText = "id";
            this.id.Name = "id";
            this.id.ReadOnly = true;
            this.id.Visible = false;
            // 
            // fileStatusDataGridViewTextBoxColumn
            // 
            this.fileStatusDataGridViewTextBoxColumn.DataPropertyName = "fileStatus";
            this.fileStatusDataGridViewTextBoxColumn.HeaderText = "Status";
            this.fileStatusDataGridViewTextBoxColumn.Name = "fileStatusDataGridViewTextBoxColumn";
            this.fileStatusDataGridViewTextBoxColumn.ReadOnly = true;
            this.fileStatusDataGridViewTextBoxColumn.Width = 80;
            // 
            // nameDataGridViewTextBoxColumn
            // 
            this.nameDataGridViewTextBoxColumn.DataPropertyName = "name";
            this.nameDataGridViewTextBoxColumn.HeaderText = "Name";
            this.nameDataGridViewTextBoxColumn.Name = "nameDataGridViewTextBoxColumn";
            this.nameDataGridViewTextBoxColumn.ReadOnly = true;
            // 
            // lastModifiedDataGridViewTextBoxColumn
            // 
            this.lastModifiedDataGridViewTextBoxColumn.DataPropertyName = "lastModified";
            this.lastModifiedDataGridViewTextBoxColumn.HeaderText = "Last Modification Time";
            this.lastModifiedDataGridViewTextBoxColumn.Name = "lastModifiedDataGridViewTextBoxColumn";
            this.lastModifiedDataGridViewTextBoxColumn.ReadOnly = true;
            this.lastModifiedDataGridViewTextBoxColumn.Width = 140;
            // 
            // authNameDataGridViewTextBoxColumn
            // 
            this.authNameDataGridViewTextBoxColumn.DataPropertyName = "authName";
            this.authNameDataGridViewTextBoxColumn.HeaderText = "Author";
            this.authNameDataGridViewTextBoxColumn.Name = "authNameDataGridViewTextBoxColumn";
            this.authNameDataGridViewTextBoxColumn.ReadOnly = true;
            // 
            // lastCommentDataGridViewTextBoxColumn
            // 
            this.lastCommentDataGridViewTextBoxColumn.DataPropertyName = "lastComment";
            this.lastCommentDataGridViewTextBoxColumn.HeaderText = "Comment on last change";
            this.lastCommentDataGridViewTextBoxColumn.Name = "lastCommentDataGridViewTextBoxColumn";
            this.lastCommentDataGridViewTextBoxColumn.ReadOnly = true;
            this.lastCommentDataGridViewTextBoxColumn.Width = 355;
            // 
            // fileBindingSource
            // 
            this.fileBindingSource.DataSource = typeof(FVMS_Client.files.File);
            // 
            // addFileButton
            // 
            this.addFileButton.Location = new System.Drawing.Point(96, 2);
            this.addFileButton.Name = "addFileButton";
            this.addFileButton.Size = new System.Drawing.Size(44, 36);
            this.addFileButton.TabIndex = 4;
            this.addFileButton.Text = "Add";
            this.addFileButton.UseVisualStyleBackColor = true;
            this.addFileButton.Click += new System.EventHandler(this.addFileButton_Click);
            // 
            // deleteFileButton
            // 
            this.deleteFileButton.Location = new System.Drawing.Point(143, 2);
            this.deleteFileButton.Name = "deleteFileButton";
            this.deleteFileButton.Size = new System.Drawing.Size(44, 36);
            this.deleteFileButton.TabIndex = 3;
            this.deleteFileButton.Text = "Delete";
            this.deleteFileButton.UseVisualStyleBackColor = true;
            this.deleteFileButton.Click += new System.EventHandler(this.deleteFileButton_Click);
            // 
            // downloadFileButton
            // 
            this.downloadFileButton.Location = new System.Drawing.Point(3, 2);
            this.downloadFileButton.Name = "downloadFileButton";
            this.downloadFileButton.Size = new System.Drawing.Size(44, 36);
            this.downloadFileButton.TabIndex = 2;
            this.downloadFileButton.Text = "Get";
            this.downloadFileButton.UseVisualStyleBackColor = true;
            this.downloadFileButton.Click += new System.EventHandler(this.downloadFileButton_Click);
            // 
            // saveFilesButton
            // 
            this.saveFilesButton.Location = new System.Drawing.Point(50, 2);
            this.saveFilesButton.Name = "saveFilesButton";
            this.saveFilesButton.Size = new System.Drawing.Size(44, 36);
            this.saveFilesButton.TabIndex = 1;
            this.saveFilesButton.Text = "Save";
            this.saveFilesButton.UseVisualStyleBackColor = true;
            this.saveFilesButton.Click += new System.EventHandler(this.saveFilesButton_Click);
            // 
            // subsolTabControl
            // 
            this.subsolTabControl.Controls.Add(this.CommentPage);
            this.subsolTabControl.Controls.Add(this.historyPage);
            this.subsolTabControl.Location = new System.Drawing.Point(203, 396);
            this.subsolTabControl.Name = "subsolTabControl";
            this.subsolTabControl.SelectedIndex = 0;
            this.subsolTabControl.Size = new System.Drawing.Size(769, 148);
            this.subsolTabControl.TabIndex = 1;
            this.subsolTabControl.Selecting += new System.Windows.Forms.TabControlCancelEventHandler(this.whenTabChanges);
            // 
            // CommentPage
            // 
            this.CommentPage.Controls.Add(this.commentLabel);
            this.CommentPage.Location = new System.Drawing.Point(4, 22);
            this.CommentPage.Name = "CommentPage";
            this.CommentPage.Padding = new System.Windows.Forms.Padding(3);
            this.CommentPage.Size = new System.Drawing.Size(761, 122);
            this.CommentPage.TabIndex = 0;
            this.CommentPage.Text = "Comment";
            this.CommentPage.UseVisualStyleBackColor = true;
            // 
            // commentLabel
            // 
            this.commentLabel.AutoSize = true;
            this.commentLabel.Location = new System.Drawing.Point(3, 3);
            this.commentLabel.Name = "commentLabel";
            this.commentLabel.Size = new System.Drawing.Size(89, 13);
            this.commentLabel.TabIndex = 0;
            this.commentLabel.Text = "No file selected...";
            // 
            // historyPage
            // 
            this.historyPage.Controls.Add(this.historyGrid);
            this.historyPage.Location = new System.Drawing.Point(4, 22);
            this.historyPage.Name = "historyPage";
            this.historyPage.Padding = new System.Windows.Forms.Padding(3);
            this.historyPage.Size = new System.Drawing.Size(761, 122);
            this.historyPage.TabIndex = 1;
            this.historyPage.Text = "History";
            this.historyPage.UseVisualStyleBackColor = true;
            // 
            // historyGrid
            // 
            this.historyGrid.AllowUserToAddRows = false;
            this.historyGrid.AllowUserToDeleteRows = false;
            this.historyGrid.AutoGenerateColumns = false;
            this.historyGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.historyGrid.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.idDataGridHistoryColumn,
            this.nameDataGridHistoryColumn,
            this.authNameDataGridHistoryColumn,
            this.lastModifiedDataGridHistoryColumn,
            this.lastCommentDataGridHistoryColumn});
            this.historyGrid.DataSource = this.fileBindingSource;
            this.historyGrid.Location = new System.Drawing.Point(0, 3);
            this.historyGrid.Name = "historyGrid";
            this.historyGrid.ReadOnly = true;
            this.historyGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.historyGrid.Size = new System.Drawing.Size(733, 119);
            this.historyGrid.TabIndex = 0;
            // 
            // idDataGridHistoryColumn
            // 
            this.idDataGridHistoryColumn.DataPropertyName = "id";
            this.idDataGridHistoryColumn.HeaderText = "Id";
            this.idDataGridHistoryColumn.Name = "idDataGridHistoryColumn";
            this.idDataGridHistoryColumn.ReadOnly = true;
            this.idDataGridHistoryColumn.Width = 30;
            // 
            // nameDataGridHistoryColumn
            // 
            this.nameDataGridHistoryColumn.DataPropertyName = "name";
            this.nameDataGridHistoryColumn.HeaderText = "Name";
            this.nameDataGridHistoryColumn.Name = "nameDataGridHistoryColumn";
            this.nameDataGridHistoryColumn.ReadOnly = true;
            // 
            // authNameDataGridHistoryColumn
            // 
            this.authNameDataGridHistoryColumn.DataPropertyName = "authName";
            this.authNameDataGridHistoryColumn.HeaderText = "Author";
            this.authNameDataGridHistoryColumn.Name = "authNameDataGridHistoryColumn";
            this.authNameDataGridHistoryColumn.ReadOnly = true;
            // 
            // lastModifiedDataGridHistoryColumn
            // 
            this.lastModifiedDataGridHistoryColumn.DataPropertyName = "lastModified";
            this.lastModifiedDataGridHistoryColumn.HeaderText = "Date";
            this.lastModifiedDataGridHistoryColumn.Name = "lastModifiedDataGridHistoryColumn";
            this.lastModifiedDataGridHistoryColumn.ReadOnly = true;
            this.lastModifiedDataGridHistoryColumn.Width = 140;
            // 
            // lastCommentDataGridHistoryColumn
            // 
            this.lastCommentDataGridHistoryColumn.DataPropertyName = "lastComment";
            this.lastCommentDataGridHistoryColumn.HeaderText = "Comment";
            this.lastCommentDataGridHistoryColumn.Name = "lastCommentDataGridHistoryColumn";
            this.lastCommentDataGridHistoryColumn.ReadOnly = true;
            this.lastCommentDataGridHistoryColumn.Width = 320;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(979, 556);
            this.Controls.Add(this.helloLabel);
            this.Controls.Add(this.logoutButton);
            this.Controls.Add(this.nameLabel);
            this.Controls.Add(this.addFileButton);
            this.Controls.Add(this.deleteFileButton);
            this.Controls.Add(this.menu_panel);
            this.Controls.Add(this.filesGridPannel);
            this.Controls.Add(this.saveFilesButton);
            this.Controls.Add(this.downloadFileButton);
            this.Controls.Add(this.subsolTabControl);
            this.Name = "MainForm";
            this.Text = "MainForm";
            this.menu_panel.ResumeLayout(false);
            this.filesGridPannel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.filesGrid)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.fileBindingSource)).EndInit();
            this.subsolTabControl.ResumeLayout(false);
            this.CommentPage.ResumeLayout(false);
            this.CommentPage.PerformLayout();
            this.historyPage.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.historyGrid)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }



        #endregion

        private System.Windows.Forms.Panel menu_panel;
        private System.Windows.Forms.Panel filesGridPannel;
        private System.Windows.Forms.TreeView foldersTree;
        private System.Windows.Forms.Button refreshButton;
        private System.Windows.Forms.DataGridView filesGrid;
        private System.Windows.Forms.BindingSource fileBindingSource;
        private System.Windows.Forms.Label helloLabel;
        public System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.Button logoutButton;
        private System.Windows.Forms.DataGridView historyGrid;
        private System.Windows.Forms.DataGridViewTextBoxColumn idDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn nameDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn authNameDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastModifiedDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastCommentDataGridHistoryColumn;
        private System.Windows.Forms.TabControl subsolTabControl;
        private System.Windows.Forms.TabPage CommentPage;
        private System.Windows.Forms.TabPage historyPage;
        private System.Windows.Forms.Label commentLabel;
        private System.Windows.Forms.Button deleteFileButton;
        private System.Windows.Forms.Button downloadFileButton;
        private System.Windows.Forms.Button saveFilesButton;
        private System.Windows.Forms.Button addFileButton;
        private System.Windows.Forms.Button collapseButton;
        private System.Windows.Forms.Button expandButton;
        private System.Windows.Forms.DataGridViewTextBoxColumn id;
        private System.Windows.Forms.DataGridViewTextBoxColumn fileStatusDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn nameDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastModifiedDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn authNameDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastCommentDataGridViewTextBoxColumn;
    }
}