import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http'
import { Observable } from 'rxjs';
import { ParkingSpace } from '../models/ParkingSpace';
@Injectable({
  providedIn: 'root'
})
export class MainService {
  apiURL:string = 'http://localhost:8080/parking-spaces';
  formData = new FormData();
//  headers = new HttpHeaders({Authorizaton: 'Basic ' + btoa('12345:root')});
  constructor(private httpClient:HttpClient) { }
  getAllParkingSpacesStatus(): Observable<ParkingSpace[]>{
    return this.httpClient.get<ParkingSpace[]>(this.apiURL);
  }
  //Retorna a vaga ocupada pelo usuário atual
  getParkingSpaceOccupiedByCurrentUser():Observable<ParkingSpace>{
    let API_URL = `${this.apiURL}/occupied-by?userId=1`;
    let requestBody = {
      userId:1
    }
    return this.httpClient.get<ParkingSpace>(API_URL);
  }
  occupyParkingSpace(userId:number,parkingSpaceLocation:string,occupationTimeInMinutes:number):Observable<any>{
    let postData = {userId,parkingSpaceLocation,occupationTimeInMinutes}
    console.log("POST DATA======>");
    console.log(postData);
    let API_URL = 'http://localhost:8080/parking-spaces/occupy'
    return this.httpClient.post<any>(API_URL,postData,{observe:'response'});
  }
  freeParkingSpace(parkingSpaceLocation:string):Observable<any>{
    let postData = {parkingSpaceLocation}
    let API_URL = 'http://localhost:8080/parking-spaces/occupy'
    return this.httpClient.post<any>(API_URL,postData,{observe:'response'});       
  }
  doLogin(cpd:any,password:any):Observable<any>{
    let data = {
      cpd:cpd,
      password:password
    }
    let API_URL = 'http://localhost:8080/login'
    return this.httpClient.post<any>(API_URL,data);
  }
}
