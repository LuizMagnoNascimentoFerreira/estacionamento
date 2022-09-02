import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ParkingSpace } from 'src/app/models/ParkingSpace';
import { User } from 'src/app/models/User';
import { MainService } from 'src/app/services/main.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  parkingSpaces:ParkingSpace[] = [{location:"",status:""}];
  selectedParkingSpace:ParkingSpace = {location:"", status:""};
  currentOccupiedParkingSpace:ParkingSpace = {location:"", status:""};
  currentOccupiedParkingSpaceLocation:string = "";
  clickable:boolean = true;
  occupationForm:boolean = false;
  loggedInUser:User = {id:0,name:"",password:""};
  constructor(private service:MainService) { }
  
  //Colore as vagas de acordo com seus status
  colorizeParkingSpaces(){
    for(let parkingSpace of this.parkingSpaces){
      let parkingSpaceDiv = document.getElementById(parkingSpace.location);
      if(parkingSpace.status == "FREE"){
        parkingSpaceDiv!.style.backgroundColor = "lightgreen";
      }else if(parkingSpace.location == this.currentOccupiedParkingSpace?.location){
        parkingSpaceDiv!.style.backgroundColor = "lightblue";
      }else{
        parkingSpaceDiv!.style.backgroundColor = "red";
      }
    }
  }
  //Libera a vaga
  cancelParkingSpaceOccupationForm(){
    document.getElementById(this.selectedParkingSpace!.location)!.style.backgroundColor = "lightgreen";
    this.selectedParkingSpace = {location:"", status:""};
    this.clickable = true;
    this.occupationForm = false;
  }
  //Abre o formulário de ocupação de vaga
  openOccupationForm(){
    this.clickable = false;
    this.occupationForm = true;
  }
  //Ocupa efetivamente a vaga
  occupyParkingSpace(userId:number,parkingSpaceLocation:string,occupationTimeInMinutes:number){
    this.service.occupyParkingSpace(userId,parkingSpaceLocation,occupationTimeInMinutes).subscribe((response)=>console.log(response.status));
  }
  //Confirma a ocupação da vaga
  confirmParkingSpaceOccupation(){
    this.clickable = true;
    this.occupationForm = false;
    this.currentOccupiedParkingSpaceLocation = this.selectedParkingSpace.location;
    let index = this.parkingSpaces.findIndex(parkingSpace => parkingSpace.location === this.currentOccupiedParkingSpaceLocation);
    this.parkingSpaces[index].status = 'OCCUPIED';
    document.getElementById(this.currentOccupiedParkingSpaceLocation)!.style.backgroundColor = "lightblue";
    let hoursInput = document.getElementById('hours') as HTMLInputElement;
    let minutesInput = document.getElementById('minutes') as HTMLInputElement;
    let occupationTime = (60 * Number(hoursInput.value)) + Number(minutesInput.value);
   // alert(this.loggedInUser.id);
   // alert(this.selectedParkingSpace.location);
    this.occupyParkingSpace(this.loggedInUser.id,this.selectedParkingSpace.location,occupationTime);
    this.startOccuptionTimer(occupationTime);
  }
  //Libera a vaga do estacionamento
  freeParkingSpaceLocation(parkingSpaceLocation:string){
    this.service.freeParkingSpace(parkingSpaceLocation).subscribe((response)=>console.log(response));
  }
  //Inicia o timer de ocupação
  startOccuptionTimer(timeout:number){
    setTimeout(()=>{
      this.freeParkingSpaceLocation(this.currentOccupiedParkingSpaceLocation);
      document.getElementById(this.currentOccupiedParkingSpaceLocation)!.style.backgroundColor='lightgreen';
      this.currentOccupiedParkingSpaceLocation = "";
      alert("A vaga foi liberada.");
    },timeout*60*1000)
  }
  selectParkingSpace(parkingSpaceLocation:string){
    if(this.clickable){
      if(this.currentOccupiedParkingSpace != null){
        alert("Não pode ocupar mais de uma vaga simultaneamente.")
      }else{
        for(let parkingSpace of this.parkingSpaces){
          if(parkingSpace.location === parkingSpaceLocation){
            this.selectedParkingSpace = parkingSpace;
          }
        }
        if(this.selectedParkingSpace?.status == "FREE"){
          this.openOccupationForm();
        }else{
          alert("A vaga já está ocupada.");
         }
      }
    }
  }
  getParkingSpaceOccupiedByCurrentUser(){
    this.service.getParkingSpaceOccupiedByCurrentUser().subscribe(currentOccupiedParkingSpace=>{
      this.currentOccupiedParkingSpace = currentOccupiedParkingSpace;
      console.log(this.currentOccupiedParkingSpace);
    });
  }
  getAllParkingSpacesStatus(){
    this.service.getAllParkingSpacesStatus().subscribe(parkingSpaces => {
      this.parkingSpaces = parkingSpaces;
      this.colorizeParkingSpaces();
    })
  }
  doLogin(username:string,password:string){
    this.service.doLogin(username,password).subscribe((generatedUser)=>{
      console.log(generatedUser);
      sessionStorage.setItem("current-user",JSON.stringify(generatedUser));
    })
  }
  ngOnInit(): void {
    let currentUser = sessionStorage.getItem('current-user');
    if(currentUser != null){
      this.loggedInUser = JSON.parse(currentUser);
    }else{
      let cpd =  window.prompt("cpd:")!;
      let password = window.prompt("password:")!;
      this.doLogin(cpd,password);
      setTimeout(()=>{location.reload()},800)
    }
    this.getParkingSpaceOccupiedByCurrentUser();
    this.getAllParkingSpacesStatus();
  }

}
