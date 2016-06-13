using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace FVMS_Client.files
{
    public class File
    {
        public string name { get; private set; }
        public int id { get; set; }
        public int pid { get; private set; }
        public String path { get; private set; }
        public String lastModified { get; private set; }
        public String lastComment { get; private set; }
        public String authName { get; private set; }
        public String boundedFilePath;
        public FileStatus fileStatus { get; set; }
        private FileSystemWatcher fileWatcher;
        public File(int id, int pid, String path, String lastModified, String lastComment, String authName)
        {
            this.id = id;
            this.pid = pid;
            this.path = path;
            this.name = Path.GetFileName(path);
            this.lastModified = lastModified;
            this.lastComment = lastComment;
            this.authName = authName;
            this.fileStatus = FileStatus.UPTODATE;
        }

        public void createFileWatcherOnFile()
        {
            this.fileWatcher = new FileSystemWatcher();
            fileWatcher.Path += Path.GetDirectoryName(boundedFilePath);
            fileWatcher.Filter = name;
            fileWatcher.NotifyFilter = NotifyFilters.FileName | NotifyFilters.LastWrite | NotifyFilters.DirectoryName | NotifyFilters.Size;
            fileWatcher.Renamed += OnRenamed;
            fileWatcher.Changed += OnChanged;
            fileWatcher.Deleted += OnDeleted;
            fileWatcher.EnableRaisingEvents = true;
        }

        private void OnDeleted(object sender, FileSystemEventArgs e)
        {
            this.fileStatus = FileStatus.UPTODATE;
        }
        private void OnRenamed(object source, RenamedEventArgs e)
        {
            this.name = Path.GetFileName(e.Name);
            fileWatcher.Path = e.FullPath;
            fileWatcher.Filter = e.Name;
            this.boundedFilePath = e.FullPath;
            fileStatus = FileStatus.CHANGED;
        }
        private void OnChanged(object source, FileSystemEventArgs e)
        {
            fileStatus = FileStatus.CHANGED;
        }

        public class FileInFolder : File
        {
            public FileInFolder(int pid, String path, String filePath,  String authName) : base(0,pid, path, "","",authName)
        {
            this.name = Path.GetFileName(filePath);
            if (!name.Equals(path))
            {
                this.path += "/" + name;
            }
            this.fileStatus = FileStatus.NEW;
            this.boundedFilePath = filePath;
        }
        }

        internal void appendContent(byte[] content)
        {
            sbyte[] sbt = new sbyte[content.Length];
            Buffer.BlockCopy(content, 0, sbt, 0, content.Length);
            try
            {
                using (var stream = new FileStream(boundedFilePath, FileMode.Append))
                {
                    BinaryWriter bw = new BinaryWriter(stream);
                    foreach (sbyte b in sbt)
                    {
                        bw.Write(b);
                    }

                   // stream.Write(content, 0, content.Length);
                }
            }
            catch (Exception _Exception)
            {
                Console.WriteLine("Exception caught in process: {0}",
                                  _Exception.ToString());
            }
        }

        internal bool checkContent(string hash)
        {
            MD5 md5 = MD5.Create();
            FileStream stream = System.IO.File.OpenRead(boundedFilePath);
            byte[] receivedHash = md5.ComputeHash(stream);
            return receivedHash.Equals(hash);
        }
    }
}
