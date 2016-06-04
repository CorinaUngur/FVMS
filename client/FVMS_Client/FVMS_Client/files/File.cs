using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FVMS_Client.files
{
    public class File
    {
        public string name { get; private set; }
        public int id { get; private set; }
        public String path { get; private set; }
        public String lastModified { get; private set; }
        public String lastComment { get; private set; }
        public String authName { get; private set; }
        public File(int id, String path, String lastModified, String lastComment, String authName)
        {
            this.id = id;
            this.path = path;
            this.name = path.Split('/').Last();
            this.lastModified = lastModified;
            this.lastComment = lastComment;
            this.authName = authName;
        }
    }
}
