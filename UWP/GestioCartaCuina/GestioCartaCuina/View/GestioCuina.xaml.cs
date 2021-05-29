using MySQL_BD;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading;
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
    public sealed partial class GestioCuina : Page
    {
        public GestioCuina()
        {
            this.InitializeComponent();
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            /*List<ComandaDB> comandes = ComandaDB.GetComandes();
           
            for(int i=0;i<comandes.Count;i++)
            {
                
                comandes[i].Plats = ComandaDB.getPlatsXComanda(comandes[i].Codi);
            }
            lstComandes.ItemsSource = comandes;*/
            executarThread();
        }

        private void executarThread()
        {
            Thread th = new Thread(actualitzaComandes);
            th.Start();
        }

        private void actualitzaComandes()
        {
            while (true)
            {

                List<ComandaDB> comandes = ComandaDB.GetComandes();

                for (int i = 0; i < comandes.Count; i++)
                {

                    comandes[i].Plats = ComandaDB.getPlatsXComanda(comandes[i].Codi);
                }

                Invoke(() =>
                {
                    lstComandes.ItemsSource = comandes;
                });
                Thread.Sleep(2000);
            }
        }

        public async void Invoke(Action action, Windows.UI.Core.CoreDispatcherPriority Priority = Windows.UI.Core.CoreDispatcherPriority.Normal)
        {
            await Windows.ApplicationModel.Core.CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(Priority, () => { action(); });
        }
    }
}
