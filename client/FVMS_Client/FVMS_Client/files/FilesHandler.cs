using FVMS_Client.beans;
using System;
using System.Collections.Generic;
using System.Collections.Concurrent;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.files
{
    class FilesHandler
    {
        private List<Folder> foldersQueue;
        private static FilesHandler instance;
        private FilesHandler()
        {
            foldersQueue = new List<Folder>();
        }
        public static FilesHandler getInstance()
        {
            if (instance == null)
            {
                instance = new FilesHandler();
            }
            return instance;
        }

        public List<Folder> initializeFolders(string projects)
        {
            foldersQueue.Clear();
           Dictionary<String, Folder> foldersMap = new Dictionary<string,Folder>();
           List<Folder> initialFolders = JSONManipulator.DeserializeList<Folder>(projects);
           foreach (Folder fld in initialFolders)
           {
               foldersQueue.Add(fld);
               foldersMap.Add(fld.name, fld);
               fld.path = fld.name;
               List<File> filesInInitialFolder = fld.GetFiles();
               for (int i = 0; i < filesInInitialFolder.Count(); i++)
               {

                   String pathSoFar = fld.name;
                   File file = filesInInitialFolder.ElementAt(i);
                   String file_path = file.path;
                   IEnumerable<string> folders = file_path.Replace('\\','/').Split('/'); ;
                   String newPath = pathSoFar;
                   if(folders.ElementAt(0).Equals(file_path)){
                       fld.addFile(file);
                   }
                   else
                   {
                        for (int j =0; j < folders.Count() - 1; j++)
                        {
                            newPath  += "/" + folders.ElementAt(j);
                            Folder parentFolder = GetFolderInMap(foldersMap, pathSoFar);
                            Folder childFolder = GetFolderInMap(foldersMap, newPath);
                            parentFolder.addChildIfNotThere(childFolder);
                            pathSoFar = newPath;
                        }
                        GetFolderInMap(foldersMap,pathSoFar).addFile(file);
                        fld.removeFile(file);
                   }
                   
               }
           }
           return foldersQueue.ToList<Folder>();
        }

        private Folder GetFolderInMap(Dictionary<String, Folder> foldersMap, String key)
        {
            Folder fileFolder;
            if (foldersMap.ContainsKey(key))
            {
                foldersMap.TryGetValue(key, out fileFolder);
            }
            else
            {
                fileFolder = new Folder(null,key.Split('/').Last());
                foldersMap.Add(key, fileFolder);
                fileFolder.path = key;
            }
            return fileFolder;
        }

        public List<Folder> getFoldersList()
        {
            return foldersQueue.ToList<Folder>();
        }

        internal List<File> getFiles(string outString)
        {
            return JSONManipulator.DeserializeList<File>(outString);
        }

    }
}
