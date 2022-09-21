// On initialise la latitude et la longitude de Paris (centre de la carte)
var lat = 46.227638;
var lon = 2.213749;
var macarte = null;
//var markerClusters; // Servira à stocker les groupes de marqueurs

// Fonction d'initialisation de la carte
    function initMap() {
		// Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"
        macarte = L.map('map').setView([lat, lon], 6);
       // markerClusters = L.markerClusterGroup(); // Nous initialisons les groupes de marqueurs
        // Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
        L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
            // Il est toujours bien de laisser le lien vers la source des données
            attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
            minZoom: 1,
            maxZoom: 20
        }).addTo(macarte);
        //macarte.addLayer(markerClusters);
	}
	
window.onload = function(){
// // Fonction d'initialisation qui s'exécute lorsque le DOM est chargé
	initMap(); 
};