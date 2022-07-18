

import { Entity, PrimaryGeneratedColumn, Column, OneToOne, JoinColumn, PrimaryColumn } from "typeorm"
import { Address } from "./address";
import { Classification } from "./classification";
import { User } from "./user";

@Entity()
export class User_Profile {

  @PrimaryGeneratedColumn("uuid")
  ID: string

  @Column("uuid")
  userID?: string;

  @Column("uuid")
  classificationID?: string;

  @Column("uuid")
  addressID?: string;

  @OneToOne(() => User)
  @JoinColumn()
  User!: User;

  @Column()
  firstName!: string;

  @Column()
  lastName!: string;

  @Column("date")
  dob?: Date;

  @Column()
  gender?: string;

  @Column({ unique: true })
  CNIC!: number;

  @OneToOne(() => Address)
  @JoinColumn()
  Address: Address;

  @OneToOne(() => Classification)
  @JoinColumn()
  Classification?: Classification;

  @Column()
  employed!: boolean;

  @Column()
  experience?: number;
}
