namespace FVMS_Client.forms
{
    partial class MessagePopup
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
            this.messageLabel = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.canelButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            this.CancelButton = canelButton;
            // 
            // messageLabel
            // 
            this.messageLabel.AutoSize = true;
            this.messageLabel.Location = new System.Drawing.Point(12, 22);
            this.messageLabel.Name = "messageLabel";
            this.messageLabel.Size = new System.Drawing.Size(225, 13);
            this.messageLabel.TabIndex = 0;
            this.messageLabel.Text = "Please selecte one or more files for this action.";
            // 
            // okButton
            // 
            this.okButton.Location = new System.Drawing.Point(234, 64);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(75, 23);
            this.okButton.TabIndex = 1;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // cancleMessagePopupButton
            // 
            this.canelButton.Location = new System.Drawing.Point(316, 64);
            this.canelButton.Name = "cancleMessagePopupButton";
            this.canelButton.Size = new System.Drawing.Size(75, 23);
            this.canelButton.TabIndex = 2;
            this.canelButton.Text = "Cancel";
            this.canelButton.UseVisualStyleBackColor = true;
            this.canelButton.Click += new System.EventHandler(this.cancleMessagePopupButton_Click);
            // 
            // MessagePopup
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(398, 99);
            this.Controls.Add(this.canelButton);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.messageLabel);
            this.Name = "MessagePopup";
            this.Text = "No File Selected";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label messageLabel;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button canelButton;


    }
}