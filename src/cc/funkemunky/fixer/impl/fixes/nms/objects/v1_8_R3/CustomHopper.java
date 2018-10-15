package cc.funkemunky.fixer.impl.fixes.nms.objects.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.*;

import java.util.List;

public class CustomHopper extends BlockContainer {
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", new Predicate() {
        public boolean a(EnumDirection enumdirection) {
            return enumdirection != EnumDirection.UP;
        }

        public boolean apply(Object object) {
            return this.a((EnumDirection)object);
        }
    });
    public static final BlockStateBoolean ENABLED = BlockStateBoolean.of("enabled");

    public CustomHopper() {
        super(Material.ORE, MaterialMapColor.m);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.DOWN).set(ENABLED, true));
        this.a(CreativeModeTab.d);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
        float f = 0.125F;
        this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
        this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
        this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        EnumDirection enumdirection1 = enumdirection.opposite();
        if (enumdirection1 == EnumDirection.UP) {
            enumdirection1 = EnumDirection.DOWN;
        }

        return this.getBlockData().set(FACING, enumdirection1).set(ENABLED, true);
    }

    public TileEntity a(World world, int i) {
        return new CustomTileHopper();
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
        super.postPlace(world, blockposition, iblockdata, entityliving, itemstack);
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof CustomTileHopper) {
                ((CustomTileHopper)tileentity).a(itemstack.getName());
            }
        }

    }

    public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
        this.e(world, blockposition, iblockdata);
    }

    public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
        if (world.isClientSide) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof CustomTileHopper) {
                entityhuman.openContainer((CustomTileHopper)tileentity);
                entityhuman.b(StatisticList.P);
            }

            return true;
        }
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        this.e(world, blockposition, iblockdata);
    }

    private void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        boolean flag = !world.isBlockIndirectlyPowered(blockposition);
        if (flag != (Boolean)iblockdata.get(ENABLED)) {
            world.setTypeAndData(blockposition, iblockdata.set(ENABLED, flag), 4);
        }

    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        TileEntity tileentity = world.getTileEntity(blockposition);
        if (tileentity instanceof CustomTileHopper) {
            InventoryUtils.dropInventory(world, blockposition, (CustomTileHopper)tileentity);
            world.updateAdjacentComparators(blockposition, this);
        }

        super.remove(world, blockposition, iblockdata);
    }

    public int b() {
        return 3;
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public static EnumDirection b(int i) {
        return EnumDirection.fromType1(i & 7);
    }

    public static boolean f(int i) {
        return (i & 8) != 8;
    }

    public boolean isComplexRedstone() {
        return true;
    }

    public int l(World world, BlockPosition blockposition) {
        return Container.a(world.getTileEntity(blockposition));
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(FACING, b(i)).set(ENABLED, f(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        byte b0 = 0;
        int i = b0 | ((EnumDirection)iblockdata.get(FACING)).a();
        if (!(Boolean)iblockdata.get(ENABLED)) {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[]{FACING, ENABLED});
    }
}

