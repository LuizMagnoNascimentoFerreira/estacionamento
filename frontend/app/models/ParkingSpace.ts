import { User } from "./User"

export interface ParkingSpace{
    id?:number;
    user?:User;
    expirationDate?:Date;
    location:string;
    status:string;
}