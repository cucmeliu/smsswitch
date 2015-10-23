using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace DotNet_Demo
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        #region 调用接口方法
        //发送短信
        private void SendSms()
        {
            TopenServiceReference.MsgSendSoapClient topen = new TopenServiceReference.MsgSendSoapClient();
            string userName = "用户名";
            string passWord = "密码";
            string mobiles = "13900000000,13800000000,13100000000,……";
            string msgContent = "短信内容(含签名)";
            string channel = "由拓鹏给您的通道编号";
            string sendResult = topen.sendMes(userName, passWord, mobiles, msgContent, channel); //此处的sendMes可能因接口文档不同而不同，请注意。返回批次号，可保存下来，作为获取发送报告凭据

            //然后，根据返回的sendResult作相应处理（返回值详见开发文档）
        }

        //取报告
        private void GetReport()
        {
            TopenServiceReference.MsgSendSoapClient topen = new TopenServiceReference.MsgSendSoapClient();
            string userName = "用户名";
            string passWord = "密码";
            string batchNumber = "1369367246688";//发送时返回的批次号，提交一次返回一个
            string report = topen.GetReport(userName, passWord, batchNumber);
        }

        //取余额
        private void GetBalance()
        {
            TopenServiceReference.MsgSendSoapClient topen = new TopenServiceReference.MsgSendSoapClient();
            string balance = topen.GetBalance("用户名", "密码");
        }

        //取余额
        private void GetMo()
        {
            TopenServiceReference.MsgSendSoapClient topen = new TopenServiceReference.MsgSendSoapClient();
            string mo = topen.GetMo("用户名", "密码");
        }
        #endregion







    }
}