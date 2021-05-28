using MySQL_BD;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Imaging;
using Windows.Storage;
using Windows.Storage.Pickers;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=234238

namespace GestioCartaCuina.View
{


    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class GestioCarta : Page
    {
        private String url = null;
        public GestioCarta()
        {
            this.InitializeComponent();

        }

        private async void Page_Loaded(object sender, RoutedEventArgs e)
        {
            lstCategoria.ItemsSource = CategoriaDB.getCategories();
            List<PlatDB> plats = PlatDB.getPlats();
            for (int i = 0; i < plats.Count; i++)
            {
                plats[i].ImatgeSource = await SaveToImageSource(plats[i].Foto);
            }

            lstPlats.ItemsSource = plats;
        }

        private void btnNetejarFiltres_Click(object sender, RoutedEventArgs e)
        {
            lstCategoria.SelectedIndex = -1;
            txtNomPlat.Text = "";
        }

        public async Task<ImageSource> SaveToImageSource(byte[] imageBuffer)
        {
            ImageSource imageSource = null;
            using (MemoryStream stream = new MemoryStream(imageBuffer))
            {
                var ras = stream.AsRandomAccessStream();
                BitmapDecoder decoder = await BitmapDecoder.CreateAsync(BitmapDecoder.JpegDecoderId, ras);
                var provider = await decoder.GetPixelDataAsync();
                byte[] buffer = provider.DetachPixelData();
                WriteableBitmap bitmap = new WriteableBitmap((int)decoder.PixelWidth, (int)decoder.PixelHeight);
                await bitmap.PixelBuffer.AsStream().WriteAsync(buffer, 0, buffer.Length);
                imageSource = bitmap;
            }
            return imageSource;
        }

        private async void lstCategoria_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lstCategoria.SelectedIndex != -1 && txtNomPlat.Text == "")
            {
                CategoriaDB c = (CategoriaDB)lstCategoria.SelectedValue;
                List<PlatDB> plats = PlatDB.getPlatsXCategoria(c.Codi);
                for (int i = 0; i < plats.Count; i++)
                {
                    plats[i].ImatgeSource = await SaveToImageSource(plats[i].Foto);
                }

                lstPlats.ItemsSource = plats;
            }
            else if (lstCategoria.SelectedIndex != -1 && txtNomPlat.Text != "")
            {
                CategoriaDB c = (CategoriaDB)lstCategoria.SelectedValue;
                List<PlatDB> plats = PlatDB.getPlatsXNomICategoria(txtNomPlat.Text, c.Codi);
                for (int i = 0; i < plats.Count; i++)
                {
                    plats[i].ImatgeSource = await SaveToImageSource(plats[i].Foto);
                }

                lstPlats.ItemsSource = plats;
            }
            else
            {
                List<PlatDB> plats = PlatDB.getPlats();
                for (int i = 0; i < plats.Count; i++)
                {
                    plats[i].ImatgeSource = await SaveToImageSource(plats[i].Foto);
                }

                lstPlats.ItemsSource = plats;
            }

            if (txtPreu.Text != "" && txtDescripcio.Text != "" && txtNom.Text != "" && lstCategoria.SelectedIndex != -1 && imgPlat.Source != null)
            {
                btnAfegir.IsEnabled = true;
            }
            else
            {
                btnAfegir.IsEnabled = false;
            }
        }

        private async void txtNomPlat_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (txtNomPlat.Text != "" && lstCategoria.SelectedIndex != -1)
            {
                CategoriaDB c = (CategoriaDB)lstCategoria.SelectedValue;
                List<PlatDB> plats = PlatDB.getPlatsXNomICategoria(txtNomPlat.Text, c.Codi);
                for (int i = 0; i < plats.Count; i++)
                {
                    plats[i].ImatgeSource = await SaveToImageSource(plats[i].Foto);
                }

                lstPlats.ItemsSource = plats;
            }
            else if (txtNomPlat.Text != "" && lstCategoria.SelectedIndex == -1)
            {
                List<PlatDB> plats = PlatDB.getPlatsXNom(txtNomPlat.Text);
                for (int i = 0; i < plats.Count; i++)
                {
                    plats[i].ImatgeSource = await SaveToImageSource(plats[i].Foto);
                }

                lstPlats.ItemsSource = plats;
            }
            else
            {
                List<PlatDB> plats = PlatDB.getPlats();
                for (int i = 0; i < plats.Count; i++)
                {
                    plats[i].ImatgeSource = await SaveToImageSource(plats[i].Foto);
                }

                lstPlats.ItemsSource = plats;
            }
        }

        private void txtPreu_TextChanged(object sender, TextChangedEventArgs e)
        {
            /*[+-]?([0-9]*[.])?[0-9]+*/

            if (!System.Text.RegularExpressions.Regex.IsMatch(txtPreu.Text, "[+-]?([0-9]*[.])?[0-9]+") && txtPreu.Text.Length > 0)
            {

                txtPreu.Text = txtPreu.Text.Remove(txtPreu.Text.Length - 1);
            }

            if(txtPreu.Text != "" && txtDescripcio.Text != "" && txtNom.Text != "" && lstCategoria.SelectedIndex != -1 && imgPlat.Source != null)
            {
                btnAfegir.IsEnabled = true;
            }
            else
            {
                btnAfegir.IsEnabled = false;
            }
        }

        private async void btnImg_Click(object sender, RoutedEventArgs e)
        {
            FileOpenPicker fp = new FileOpenPicker();
            fp.FileTypeFilter.Add(".jpg");
            fp.FileTypeFilter.Add(".png");

            StorageFile sf = await fp.PickSingleFileAsync();
            // Cerca la carpeta de dades de l'aplicació, dins de ApplicationData
            var folder = ApplicationData.Current.LocalFolder;
            // Dins de la carpeta de dades, creem una nova carpeta "icons"
            var iconsFolder = await folder.CreateFolderAsync("icons", CreationCollisionOption.OpenIfExists);
            // Creem un nom usant la data i hora, de forma que no poguem repetir noms.
            if (sf != null)
            {
                string name = (DateTime.Now).ToString("yyyyMMddhhmmss") + "_" + sf.Name;
                // Copiar l'arxiu triat a la carpeta indicada, usant el nom que hem muntat
                StorageFile copiedFile = await sf.CopyAsync(iconsFolder, name);
                // Crear una imatge en memòria (BitmapImage) a partir de l'arxiu copiat a ApplicationData
                BitmapImage tmpBitmap = new BitmapImage(new Uri(copiedFile.Path));
                imgPlat.Source = new BitmapImage(new Uri(copiedFile.Path));
                url = GetBase64StringForImage(copiedFile.Path);

            }
        }

        public static string GetBase64StringForImage(string imgPath)
        {
            byte[] imageBytes = System.IO.File.ReadAllBytes(imgPath);
            string base64String = Convert.ToBase64String(imageBytes);
            return base64String;
        }

        private void btnAfegir_Click(object sender, RoutedEventArgs e)
        {
            long codi = PlatDB.getNumPlats();
            String nom = txtNom.Text;
            String descripcio = txtDescripcio.Text;
            double preu = double.Parse(txtPreu.Text, CultureInfo.InvariantCulture);
            bool disponible = (bool)chbDisponible.IsChecked;
            CategoriaDB c = (CategoriaDB)lstCategoria.SelectedValue;

            PlatDB p = new PlatDB(codi+1, nom, descripcio, preu, url, disponible, c.Codi);
            p.Insert();

            lstPlats.ItemsSource = null;
            lstCategoria.SelectedIndex = -1;
            txtNomPlat.Text = "";
            lstPlats.ItemsSource = PlatDB.getPlats();
        }

        private void lstPlats_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if(lstPlats.SelectedIndex == -1)
            {
                btnEliminar.IsEnabled = false;
            }
            else
            {
                btnEliminar.IsEnabled = true;
            }
        }

        private async void btnEliminar_Click(object sender, RoutedEventArgs e)
        {
            PlatDB plat = (PlatDB)lstPlats.SelectedValue;
            int numeroLiniesComanda = LiniaComandaDB.getNumeroComandesPlat(plat.Codi);
            if(numeroLiniesComanda == 0)
            {
                ContentDialog esborrar = new ContentDialog
                {
                    Title = "Esborrar",
                    Content = "Segur que vols esborrar aquest plat?",
                    PrimaryButtonText = "OK",
                    CloseButtonText = "NO"

                };

                ContentDialogResult result = await esborrar.ShowAsync();

                if (result == ContentDialogResult.Primary)
                {
                    plat.Delete();
                    lstCategoria.SelectedIndex = -1;
                    txtNomPlat.Text = "";
                    lstPlats.ItemsSource = null;
                    lstPlats.ItemsSource = PlatDB.getPlats();
                }
            }else
            {
                ContentDialog no_esborrar = new ContentDialog
                {
                    Title = "No es pot esborrar",
                    Content = "No es pot esborrar aquest plat amb linies de comanda actives",
                    PrimaryButtonText = "OK"

                };

                ContentDialogResult result = await no_esborrar.ShowAsync();
            }
        }
    }
}
