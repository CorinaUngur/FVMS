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
        private ConcurrentQueue<File> concurrFiles;

        private ConcurrentQueue<Folder> childs;
        private string folder_name;
        public string path;
//        public Folder( List<File> files, String name)
        public Folder(List<File> files, String name)
        {
            this.name = name;
            concurrFiles = new ConcurrentQueue<File>();
            childs = new ConcurrentQueue<Folder>();
            if(files != null)
            foreach(File f in files){
                this.concurrFiles.Enqueue(f);
            }
        }

        public void addFile(File file){
            concurrFiles.Enqueue(file);
        }
        public void addChild(Folder folder)
        {
            childs.Enqueue(folder);
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
    }
}
