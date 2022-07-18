import { Entity, PrimaryGeneratedColumn, Column, OneToMany } from "typeorm"
import { Company } from "./company";

@Entity()
export class Industry {

  @PrimaryGeneratedColumn("uuid")
  ID: string;

  @Column({ unique: true })
  name: string;

  @OneToMany(() => Company, Company => Company.Industry)
  Companies!: Company[];

}
