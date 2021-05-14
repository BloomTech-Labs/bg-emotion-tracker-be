package com.lambdaschool.oktafoundation.views;


import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.models.Member;

import java.util.List;



public class SearchPost {
   private List<CAID> caids;
   private List<Member> members;
   public SearchPost (){

   }

   public List<CAID> getActivities() {
      return caids;
   }

   public void setActivities(List<CAID> caids) {
      this.caids = caids;
   }

   public List<Member> getMembers() {
      return members;
   }

   public void setMembers(List<Member> members) {
      this.members = members;
   }
}
