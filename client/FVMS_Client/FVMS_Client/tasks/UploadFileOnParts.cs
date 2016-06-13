using FVMS_Client.files;
using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace FVMS_Client.tasks
{
    class UploadFileOnParts : Task
    {
        private FVMS_Client.files.File file;
        public UploadFileOnParts(string queue, FVMS_Client.files.File file) : base(queue)
        {
            this.file = file;
        }
        public override void execute()
        {
            file.fileStatus = FileStatus.SENDING;
            FileStream fileStream = new FileStream(file.boundedFilePath, FileMode.Open, FileAccess.Read);
            byte[] fileBlock;
            long fileSize = fileStream.Length;
            long lastBlockSize = fileSize % Config.blockSize;
            long noOfBlocks = fileSize / Config.blockSize;
            long position = 0;
            while (noOfBlocks > 0)
            {
                fileStream.Seek(position, SeekOrigin.Begin);
                fileBlock = sendOneBlock(fileStream, Config.blockSize);
                position += Config.blockSize;
                noOfBlocks--;
            }
            if (lastBlockSize > 0)
            {
                sendOneBlock(fileStream, lastBlockSize);
            }
            fileStream.Close();
            file.fileStatus = FileStatus.UPTODATE;
        }

        private byte[] sendOneBlock(FileStream fileStream, long blockSize)
        {
            byte[] fileBlock;
            fileBlock = new byte[blockSize];
            fileStream.Read(fileBlock, 0, fileBlock.Length);
            Dictionary<String, object> message = new Dictionary<string, object>();
            message.Add("fileBlock", fileBlock);
            Controller.getInstance().sendMessage(message, Queue);
            return fileBlock;
        }
    }
}
