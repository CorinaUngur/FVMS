namespace FVMS_Client.forms
{
    partial class SaveFilesPopup
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
            this.describeChangesLabel = new System.Windows.Forms.Label();
            this.commitMessageTextBox = new System.Windows.Forms.RichTextBox();
            this.filesListBox = new System.Windows.Forms.ListBox();
            this.fileBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.listFilesLabel = new System.Windows.Forms.Label();
            this.cancelSavingFilesButton = new System.Windows.Forms.Button();
            this.saveFilesButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.fileBindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // describeChangesLabel
            // 
            this.describeChangesLabel.AutoSize = true;
            this.describeChangesLabel.Location = new System.Drawing.Point(25, 28);
            this.describeChangesLabel.Name = "describeChangesLabel";
            this.describeChangesLabel.Size = new System.Drawing.Size(163, 13);
            this.describeChangesLabel.TabIndex = 1;
            this.describeChangesLabel.Text = "Describe the changes you made:";
            // 
            // commitMessageTextBox
            // 
            this.commitMessageTextBox.Location = new System.Drawing.Point(28, 44);
            this.commitMessageTextBox.Name = "commitMessageTextBox";
            this.commitMessageTextBox.Size = new System.Drawing.Size(445, 123);
            this.commitMessageTextBox.TabIndex = 2;
            this.commitMessageTextBox.Text = "";
            // 
            // listBox1
            // 
            this.filesListBox.FormattingEnabled = true;
            this.filesListBox.Location = new System.Drawing.Point(28, 191);
            this.filesListBox.Name = "listBox1";
            this.filesListBox.Size = new System.Drawing.Size(445, 95);
            this.filesListBox.TabIndex = 3;
            // 
            // fileBindingSource
            // 
            this.fileBindingSource.DataSource = typeof(FVMS_Client.files.File);
            // 
            // listFilesLabel
            // 
            this.listFilesLabel.AutoSize = true;
            this.listFilesLabel.Location = new System.Drawing.Point(28, 175);
            this.listFilesLabel.Name = "listFilesLabel";
            this.listFilesLabel.Size = new System.Drawing.Size(143, 13);
            this.listFilesLabel.TabIndex = 4;
            this.listFilesLabel.Text = "List of files you want to save:";
            // 
            // cancelSavingFilesButton
            // 
            this.cancelSavingFilesButton.Location = new System.Drawing.Point(395, 306);
            this.cancelSavingFilesButton.Name = "cancelSavingFilesButton";
            this.cancelSavingFilesButton.Size = new System.Drawing.Size(75, 23);
            this.cancelSavingFilesButton.TabIndex = 5;
            this.cancelSavingFilesButton.Text = "Cancel";
            this.cancelSavingFilesButton.UseVisualStyleBackColor = true;
            this.cancelSavingFilesButton.Click += new System.EventHandler(this.cancelSavingFilesButton_Click);
            // 
            // saveFilesButton
            // 
            this.saveFilesButton.Location = new System.Drawing.Point(314, 306);
            this.saveFilesButton.Name = "saveFilesButton";
            this.saveFilesButton.Size = new System.Drawing.Size(75, 23);
            this.saveFilesButton.TabIndex = 6;
            this.saveFilesButton.Text = "Save";
            this.saveFilesButton.UseVisualStyleBackColor = true;
            this.saveFilesButton.Click += new System.EventHandler(this.saveFilesButton_Click);
            // 
            // SaveFilesPopup
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(485, 341);
            this.Controls.Add(this.saveFilesButton);
            this.Controls.Add(this.cancelSavingFilesButton);
            this.Controls.Add(this.listFilesLabel);
            this.Controls.Add(this.filesListBox);
            this.Controls.Add(this.commitMessageTextBox);
            this.Controls.Add(this.describeChangesLabel);
            this.Name = "SaveFilesPopup";
            this.Text = "Save File";
            ((System.ComponentModel.ISupportInitialize)(this.fileBindingSource)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label describeChangesLabel;
        private System.Windows.Forms.RichTextBox commitMessageTextBox;
        private System.Windows.Forms.ListBox filesListBox;
        private System.Windows.Forms.BindingSource fileBindingSource;
        private System.Windows.Forms.Label listFilesLabel;
        private System.Windows.Forms.Button cancelSavingFilesButton;
        private System.Windows.Forms.Button saveFilesButton;

    }
}