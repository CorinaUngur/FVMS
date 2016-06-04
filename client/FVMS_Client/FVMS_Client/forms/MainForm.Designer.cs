using FVMS_Client.tools;
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
            this.helloLabel = new System.Windows.Forms.Label();
            this.nameLabel = new System.Windows.Forms.Label();
            this.button3 = new System.Windows.Forms.Button();
            this.foldersTree = new System.Windows.Forms.TreeView();
            this.logoutButton = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.richTextBox1 = new System.Windows.Forms.RichTextBox();
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.panel3 = new System.Windows.Forms.Panel();
            this.filesGrid = new System.Windows.Forms.DataGridView();
            this.nameDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lastModifiedDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.authNameDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lastCommentDataGridViewTextBoxColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.fileBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.hisotryPanel = new System.Windows.Forms.Panel();
            this.historyGrid = new System.Windows.Forms.DataGridView();
            this.idDataGridHistoryColumn = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.name = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.authNameDataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lastModifiedDataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.lastCommentDataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.menu_panel.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel3.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.filesGrid)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.fileBindingSource)).BeginInit();
            this.hisotryPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.historyGrid)).BeginInit();
            this.SuspendLayout();
            // 
            // menu_panel
            // 
            this.menu_panel.Controls.Add(this.helloLabel);
            this.menu_panel.Controls.Add(this.nameLabel);
            this.menu_panel.Controls.Add(this.button3);
            this.menu_panel.Controls.Add(this.foldersTree);
            this.menu_panel.Controls.Add(this.logoutButton);
            this.menu_panel.Location = new System.Drawing.Point(0, 1);
            this.menu_panel.Name = "menu_panel";
            this.menu_panel.Size = new System.Drawing.Size(133, 543);
            this.menu_panel.TabIndex = 0;
            // 
            // helloLabel
            // 
            this.helloLabel.AutoSize = true;
            this.helloLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.helloLabel.Location = new System.Drawing.Point(42, 5);
            this.helloLabel.Name = "helloLabel";
            this.helloLabel.Size = new System.Drawing.Size(44, 17);
            this.helloLabel.TabIndex = 5;
            this.helloLabel.Text = "Hello,";
            // 
            // nameLabel
            // 
            this.nameLabel.AutoSize = true;
            this.nameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.nameLabel.Location = new System.Drawing.Point(42, 22);
            this.nameLabel.Name = "nameLabel";
            this.nameLabel.Size = new System.Drawing.Size(47, 17);
            this.nameLabel.TabIndex = 4;
            this.nameLabel.Text = "buddy";
            // 
            // button3
            // 
            this.button3.BackgroundImage = global::FVMS_Client.Properties.Resources.ie_refresh_button;
            this.button3.Location = new System.Drawing.Point(102, 42);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(22, 21);
            this.button3.TabIndex = 3;
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // foldersTree
            // 
            this.foldersTree.Location = new System.Drawing.Point(3, 42);
            this.foldersTree.Name = "foldersTree";
            this.foldersTree.Size = new System.Drawing.Size(121, 498);
            this.foldersTree.TabIndex = 2;
            this.foldersTree.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.foldersTree_AfterSelect);
            // 
            // logoutButton
            // 
            this.logoutButton.BackgroundImage = global::FVMS_Client.Properties.Resources.logout;
            this.logoutButton.Location = new System.Drawing.Point(3, 5);
            this.logoutButton.Name = "logoutButton";
            this.logoutButton.Size = new System.Drawing.Size(33, 36);
            this.logoutButton.TabIndex = 0;
            this.logoutButton.UseVisualStyleBackColor = true;
            this.logoutButton.Click += new System.EventHandler(this.logOutButton_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(139, 6);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 1;
            this.button2.Text = "Page2";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.Page2Button_Click);
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.panel2.Controls.Add(this.richTextBox1);
            this.panel2.Controls.Add(this.progressBar1);
            this.panel2.Location = new System.Drawing.Point(139, 35);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(744, 361);
            this.panel2.TabIndex = 1;
            // 
            // richTextBox1
            // 
            this.richTextBox1.Location = new System.Drawing.Point(303, 256);
            this.richTextBox1.Name = "richTextBox1";
            this.richTextBox1.Size = new System.Drawing.Size(100, 96);
            this.richTextBox1.TabIndex = 1;
            this.richTextBox1.Text = "";
            // 
            // progressBar1
            // 
            this.progressBar1.Location = new System.Drawing.Point(244, 187);
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(100, 23);
            this.progressBar1.TabIndex = 0;
            // 
            // panel3
            // 
            this.panel3.BackColor = System.Drawing.SystemColors.ControlLight;
            this.panel3.Controls.Add(this.filesGrid);
            this.panel3.Location = new System.Drawing.Point(139, 35);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(744, 361);
            this.panel3.TabIndex = 0;
            // 
            // filesGrid
            // 
            this.filesGrid.AllowUserToAddRows = false;
            this.filesGrid.AllowUserToDeleteRows = false;
            this.filesGrid.AutoGenerateColumns = false;
            this.filesGrid.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.filesGrid.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.filesGrid.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.None;
            this.filesGrid.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.Single;
            this.filesGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.filesGrid.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.nameDataGridViewTextBoxColumn,
            this.lastModifiedDataGridViewTextBoxColumn,
            this.authNameDataGridViewTextBoxColumn,
            this.lastCommentDataGridViewTextBoxColumn});
            this.filesGrid.DataSource = this.fileBindingSource;
            this.filesGrid.GridColor = System.Drawing.SystemColors.ControlLight;
            this.filesGrid.Location = new System.Drawing.Point(3, 8);
            this.filesGrid.Name = "filesGrid";
            this.filesGrid.ReadOnly = true;
            this.filesGrid.Size = new System.Drawing.Size(738, 349);
            this.filesGrid.TabIndex = 0;
            // 
            // nameDataGridViewTextBoxColumn
            // 
            this.nameDataGridViewTextBoxColumn.DataPropertyName = "name";
            this.nameDataGridViewTextBoxColumn.HeaderText = "Name";
            this.nameDataGridViewTextBoxColumn.Name = "nameDataGridViewTextBoxColumn";
            this.nameDataGridViewTextBoxColumn.ReadOnly = true;
            this.nameDataGridViewTextBoxColumn.Width = 130;
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
            this.authNameDataGridViewTextBoxColumn.HeaderText = "Last change author";
            this.authNameDataGridViewTextBoxColumn.Name = "authNameDataGridViewTextBoxColumn";
            this.authNameDataGridViewTextBoxColumn.ReadOnly = true;
            this.authNameDataGridViewTextBoxColumn.Width = 130;
            // 
            // lastCommentDataGridViewTextBoxColumn
            // 
            this.lastCommentDataGridViewTextBoxColumn.DataPropertyName = "lastComment";
            this.lastCommentDataGridViewTextBoxColumn.HeaderText = "Comment on last change";
            this.lastCommentDataGridViewTextBoxColumn.Name = "lastCommentDataGridViewTextBoxColumn";
            this.lastCommentDataGridViewTextBoxColumn.ReadOnly = true;
            this.lastCommentDataGridViewTextBoxColumn.Width = 300;
            // 
            // fileBindingSource
            // 
            this.fileBindingSource.DataSource = typeof(FVMS_Client.files.File);
            // 
            // hisotryPanel
            // 
            this.hisotryPanel.Controls.Add(this.historyGrid);
            this.hisotryPanel.Location = new System.Drawing.Point(139, 402);
            this.hisotryPanel.Name = "hisotryPanel";
            this.hisotryPanel.Size = new System.Drawing.Size(741, 142);
            this.hisotryPanel.TabIndex = 2;
            // 
            // historyGrid
            // 
            this.historyGrid.AutoGenerateColumns = false;
            this.historyGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.historyGrid.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.idDataGridHistoryColumn,
            this.name,
            this.authNameDataGridViewTextBoxColumn1,
            this.lastModifiedDataGridViewTextBoxColumn1,
            this.lastCommentDataGridViewTextBoxColumn1});
            this.historyGrid.DataSource = this.fileBindingSource;
            this.historyGrid.Location = new System.Drawing.Point(3, 3);
            this.historyGrid.Name = "historyGrid";
            this.historyGrid.Size = new System.Drawing.Size(741, 136);
            this.historyGrid.TabIndex = 0;
            this.historyGrid.RowEnter += new System.Windows.Forms.DataGridViewCellEventHandler(this.whenFileSelect);
            //
            // idDataGridHistoryColumn
            // 
            this.idDataGridHistoryColumn.DataPropertyName = "id";
            this.idDataGridHistoryColumn.HeaderText = "Id";
            this.idDataGridHistoryColumn.Name = "idDataGridHistoryColumn";
            this.idDataGridHistoryColumn.ReadOnly = true;
            this.idDataGridHistoryColumn.Width = 50;
            // 
            // name
            // 
            this.name.DataPropertyName = "name";
            this.name.HeaderText = "Name";
            this.name.Name = "name";
            this.name.ReadOnly = true;
            this.name.Width = 130;
            // 
            // authNameDataGridViewTextBoxColumn1
            // 
            this.authNameDataGridViewTextBoxColumn1.DataPropertyName = "authName";
            this.authNameDataGridViewTextBoxColumn1.HeaderText = "Author";
            this.authNameDataGridViewTextBoxColumn1.Name = "authNameDataGridViewTextBoxColumn1";
            this.authNameDataGridViewTextBoxColumn1.ReadOnly = true;
            this.authNameDataGridViewTextBoxColumn1.Width = 130;
            // 
            // lastModifiedDataGridViewTextBoxColumn1
            // 
            this.lastModifiedDataGridViewTextBoxColumn1.DataPropertyName = "lastModified";
            this.lastModifiedDataGridViewTextBoxColumn1.HeaderText = "Date";
            this.lastModifiedDataGridViewTextBoxColumn1.Name = "lastModifiedDataGridViewTextBoxColumn1";
            this.lastModifiedDataGridViewTextBoxColumn1.ReadOnly = true;
            this.lastModifiedDataGridViewTextBoxColumn1.Width = 300;
            // 
            // lastCommentDataGridViewTextBoxColumn1
            // 
            this.lastCommentDataGridViewTextBoxColumn1.DataPropertyName = "lastComment";
            this.lastCommentDataGridViewTextBoxColumn1.HeaderText = "Comment";
            this.lastCommentDataGridViewTextBoxColumn1.Name = "lastCommentDataGridViewTextBoxColumn1";
            this.lastCommentDataGridViewTextBoxColumn1.ReadOnly = true;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(895, 556);
            this.Controls.Add(this.hisotryPanel);
            this.Controls.Add(this.menu_panel);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.panel2);
            this.Name = "MainForm";
            this.Text = "MainForm";
            this.menu_panel.ResumeLayout(false);
            this.menu_panel.PerformLayout();
            this.panel2.ResumeLayout(false);
            this.panel3.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.filesGrid)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.fileBindingSource)).EndInit();
            this.hisotryPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.historyGrid)).EndInit();
            this.ResumeLayout(false);

        }



        #endregion

        private System.Windows.Forms.Panel menu_panel;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.RichTextBox richTextBox1;
        private System.Windows.Forms.ProgressBar progressBar1;
        private System.Windows.Forms.TreeView foldersTree;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.DataGridView filesGrid;
        private System.Windows.Forms.BindingSource fileBindingSource;
        private System.Windows.Forms.DataGridViewTextBoxColumn nameDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastModifiedDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastCommentDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn authNameDataGridViewTextBoxColumn;
        private System.Windows.Forms.Label helloLabel;
        public System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.Button logoutButton;
        private System.Windows.Forms.Panel hisotryPanel;
        private System.Windows.Forms.DataGridView historyGrid;
        private System.Windows.Forms.DataGridViewTextBoxColumn authNameDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastModifiedDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastCommentDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn idDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn nameDataGridHistoryColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn authNameDataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastModifiedDataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn lastCommentDataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn name;
    }
}