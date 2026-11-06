public class ControllerModel {
    private ModelConnect4 partie;

    public ControllerModel(ModelConnect4 partie){
        this.partie = partie;
    }

    public void getColChoix(int col){
        this.partie.setColChoix(col);
    }
}