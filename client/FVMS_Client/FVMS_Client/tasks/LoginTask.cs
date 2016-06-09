using FVMS_Client.tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FVMS_Client.tasks
{
    class LoginTask : RequestTask
    {
        private String username;
        private String password;

        public LoginTask(String username, String password) : base(Queues.QLogin)
        {
            this.username = username;
            this.password = password;
        }
        public override Dictionary<string, object> prepareMessage()
        {
            Dictionary<String, Object> credentials = new Dictionary<string, Object>();
            credentials.Add("username", username);
            credentials.Add("password", password);
            return credentials;
            
        }

        public override void treatResponse(Dictionary<string, object> response)
        {
            Object authorization;
            if (response.TryGetValue("authorized", out authorization))
            {
                int authInt = Int32.Parse(authorization.ToString());
                if (authInt.Equals(1))
                {
                    Object ouid;
                    Object username;
                    response.TryGetValue("uid", out ouid);
                    response.TryGetValue("username", out username);
                    LoggedUser.Name = username.ToString();
                    LoggedUser.uid = Int32.Parse(ouid.ToString());
                    FormsHandler.setLoginResponse("Authorized");
                    FormsHandler.ReplaceLoginWithMainForm();
                    Ctrl.Init();
                }
                else
                {
                    FormsHandler.setLoginResponse("Not authorized");
                }
            }
        }
    }
}
