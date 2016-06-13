package connection.tools;

public class FileBlockMessage {
    String Command;
    int BlockNo;
    int Offset;
    int BlockSize;
    byte[] Content_block;

    public FileBlockMessage(String Command,int BlockNo, int Offset, int BlockSize, byte[] ContentBlock)
    {
        this.BlockNo = BlockNo;
        this.BlockSize = BlockSize;
        this.Command = Command;
        this.Content_block = ContentBlock;
        this.Offset = Offset;
    }


}
