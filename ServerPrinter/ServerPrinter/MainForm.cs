using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.ServiceModel;
using System.Drawing.Printing;

namespace ServerPrinter
{
    public partial class MainForm : Form
    {

        private PrintDocument printDoc = new PrintDocument();
        private ServiceHost Host;

        String textToPrint;
        public MainForm()
        {
            InitializeComponent();
        }

        private void Main_FormClosed(object sender, FormClosedEventArgs e)
        {
            Host.Close();
            Application.Exit();
        }
        public string DoPrint(string data)
        {
            lblData.Visible = true;
            lblData.Text = data;
            textToPrint = data;
            try
            {
                printDoc.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
                printDoc.Print();
                return "Succes print : " + data;
            }
            catch (Exception e)
            {
                return "Error :" + e.Message;
            }
        }
        private void pd_PrintPage(object sender, PrintPageEventArgs e)
        {
            Single yPos = 100;
            Single leftMargin = e.MarginBounds.Left;
            Single topMargin = e.MarginBounds.Top;
            using (Font printFont = new Font("Arial", 14.0f))
            {
                e.Graphics.DrawString(textToPrint, printFont, Brushes.Black, leftMargin, yPos, new StringFormat());
            }
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            try
            {
                Host = new ServiceHost(typeof(Printer));
                Host.Open();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
