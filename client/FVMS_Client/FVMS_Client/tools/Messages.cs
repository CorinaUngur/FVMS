using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tools
{
    public static class Messages
    {
        public static string Warning_FilesWillBeOveritten = "You have selected files that you already downloaded and changed. If you perform this action you won't be able to save the changes you already made. If you click OK the action will be performed.";
        public static string Attention_SelectOneFile = "You need to select one file to save.";
        public static string Attention_SelectOneChangedFile = "You need to select one changed file to save.";
        public static string Attention_NoFileSelected = "You need to select at least one file";
        public static string Attention_NoFolderSelected = "You need to select at least one folder for this action";
        public static string Attention_NotAutorized = "You are not authorized for this action";
    }
}
