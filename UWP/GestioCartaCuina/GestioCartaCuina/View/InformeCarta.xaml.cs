using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=234238

namespace GestioCartaCuina.View
{
    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class InformeCarta : Page
    {
        public InformeCarta()
        {
            this.InitializeComponent();
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            Uri uri = new Uri("http://51.68.224.27:8080/jasperserver/flow.html?" +
                "_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2F" +
                "dam2-averdes%2FJasperProjecte&reportUnit=%2Fdam2-averdes%2" +
                "FJasperProjecte%2FReportCarta&standAlone=true");

            webInforme.Navigate(uri);
        }
    }
}
