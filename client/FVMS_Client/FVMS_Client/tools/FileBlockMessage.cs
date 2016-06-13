using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tools
{
    class FileBlockMessage
    {
        String Command { get; set; }
        int BlockNo { get; set; }
        int Offset { get; set; }
        int BlockSize { get; set; }
        String Hash { get; set; }
        byte[] Content_block { get; set; }

        public FileBlockMessage(String Command, int BlockNo, int Offset, int BlockSize, String Hash, byte[] ContentBlock)
        {
            this.BlockNo = BlockNo;
            this.BlockSize = BlockSize;
            this.Command = Command;
            this.Content_block = ContentBlock;
            this.Hash = Hash;
            this.Offset = Offset;
        }

    }
}
