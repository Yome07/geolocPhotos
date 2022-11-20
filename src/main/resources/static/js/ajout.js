// On initialise la latitude et la longitude de Paris (centre de la carte)
var lat = 46.227638;
var lon = 2.213749;
var macarte = null;

    // Fonction d'initialisation de la carte
    function initMap() {
        
// Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"
        macarte = L.map('map').setView([lat, lon], 6);
        
        // Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
        L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
            // Il est toujours bien de laisser le lien vers la source des données
            attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
            minZoom: 1,
            maxZoom: 20
        }).addTo(macarte);
        

        function onMapClick(e) {
		
            L.marker([e.latlng.lat, e.latlng.lng]).addTo(macarte).bindPopup('Vous avez cliqué sur la carte à la position ' + e.latlng.toString()).openPopup;
            document.getElementById('latitude').value = e.latlng.lat;
            document.getElementById('longitude').value = e.latlng.lng;
            document.getElementById('showLatitude').innerHTML = e.latlng.lat;
            document.getElementById('showLongitude').innerHTML = e.latlng.lng;

		}

        
        
        macarte.on('click', onMapClick);

     }
 window.onload = function(){
 // Fonction d'initialisation qui s'exécute lorsque le DOM est chargé
 initMap(); 
 };
 
 // Affichage de l'image avant son téléchargement
 $(document).ready(function() {
    		$('#fileImage').change(function(){
    			showImageThumbnail(this);
    		});
    	}
    	);
    	
    	function showImageThumbnail(fileInput){
    		file = fileInput.files[0];
    		reader = new FileReader();
    		
    		reader.onload = function(e) {
    			$("#thumbnail").attr("src", e.target.result);
    		};
    		
    		reader.readAsDataURL(file);
    	}

