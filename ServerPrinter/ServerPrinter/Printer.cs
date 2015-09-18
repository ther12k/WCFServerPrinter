using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace ServerPrinter
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Printer" in both code and config file together.
    public class Printer : IPrinter
    {
        public string DoPrint(string data)
        {
            MainForm mainForm = (MainForm)System.Windows.Forms.Application.OpenForms[0];
            return mainForm.DoPrint(data);
        }
    }
}
