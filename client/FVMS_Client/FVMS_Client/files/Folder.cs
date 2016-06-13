using System;
using System.Collections.Concurrent;
using System.Linq;
using System.Text;
using System.Threading;
using FVMS_Client.files;
using System.Collections.Generic;

namespace FVMS_Client.beans
{
    public class Folder
    {
        public String name { get; set; }
        private File[] files {get; set;}
        private List<File> concurrFiles;

        private List<Folder> childs;
        private string folder_name;
        public string path;
        public int pid { get; set; }
//        public Folder( List<File> files, String name)
        public Folder(List<File> files, String name)
        {
            this.name = name;
            concurrFiles = new List<File>();
            childs = new List<Folder>();
            if(files != null)
            foreach(File f in files){
                this.concurrFiles.Add(f);
            }
        }

        public void addFile(File file){
            concurrFiles.Add(file);
        }
        public void addChildIfNotThere(Folder folder)
        {
            if (!childs.Contains(folder))
            {
                childs.Add(folder);
                folder.pid = this.pid;
            }
        }

        internal int GetFilesCount()
        {
            return concurrFiles.Count();
        }

        internal File getFile(int i)
        {
            return concurrFiles.ElementAt(i);
        }

        public IEnumerable<Folder> getChilds()
        {
            return childs.AsEnumerable();
        }

        internal List<File> GetFiles()
        {
            return concurrFiles.ToList<File>();
        }

        internal void removeFile(File file)
        {
            concurrFiles.Remove(file);
        }
    }
}
