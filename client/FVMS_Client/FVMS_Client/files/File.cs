using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace FVMS_Client.files
{
    public class File
    {
        public string name { get; private set; }
        public int id { get; private set; }
        public int pid { get; private set; }
        public String path { get; private set; }
        public String lastModified { get; private set; }
        public String lastComment { get; private set; }
        public String authName { get; private set; }
        private String boundedFilePath;
        public FileStatus fileStatus { get; set; }
        public byte[] file_content { get; private set; }
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

        public void setFileOnDisk(String file_path, byte[] file_content)
        {
            this.boundedFilePath = Path.GetFullPath(file_path);
            writeFileOnDisk(file_content);
            createFileWatcherOnFile();
        }

        private void createFileWatcherOnFile()
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

        private void writeFileOnDisk(byte[] file_content)
        {
            FileStream fs = System.IO.File.Create(boundedFilePath);
            fs.Write(file_content, 0, file_content.Count());
            fs.Flush();
            fs.Close();
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

        internal void createContent()
        {
            FileStream fs = System.IO.File.OpenRead(boundedFilePath);
            file_content = new byte[fs.Length];
            fs.Read(file_content, 0, (int)fs.Length);
            fs.Close();
            //TODO Write on steps!!!!
        }

        public class FileInFolder : File
        {
            public FileInFolder(int pid, String path, String filePath,  String authName) : base(0,pid, path, "","",authName)
        {
            this.name = Path.GetFileName(filePath);
            this.path += "/" + name;
            this.fileStatus = FileStatus.NEW;
            this.boundedFilePath = filePath;
        }
        }
    }
}
