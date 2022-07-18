import { Entity, PrimaryGeneratedColumn, Column } from "typeorm"

@Entity()
export class Instituation {

  @PrimaryGeneratedColumn("uuid")
  ID: string;
  
  @Column({unique: true})
  name: string;

}
